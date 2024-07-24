package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.WarpModel;
import dev.zoty.okaerinasai.utils.Teleportation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class WarpCommand implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return executeCommand(player, command, label, args);
        } else {
            return true;
        }
    }

    private boolean executeCommand(Player player, Command command, String label, String[] args) {
        String warpLabel;
        if (args.length > 0) {
            warpLabel = args[0];
        } else {
            warpLabel = Settings.getDefaultWarp();
        }

        try {
            int warpCount = WarpModel.getNumberOfWarps();

            if (warpCount == 0) {
                player.sendMessage(ChatColor.RED + "There are no registered warps.");
                return true;
            }

            Location location = WarpModel.getWarpLocation(warpLabel);
            if (location == null) {
                player.sendMessage(ChatColor.RED + "This warp does not exist!");
                return true;
            } else {
                if (!this.cooldown.containsKey(player.getUniqueId())) {
                    this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                    teleport(player, location);
                } else {
                    // Calculate difference in ms
                    long timeElapsed = System.currentTimeMillis() - this.cooldown.get(player.getUniqueId());

                    // Gets the config cooldown in seconds and convert it to milliseconds
                    long cooldownInMillis = (long) (Settings.getWarpCooldown() * 1000);
                    if (timeElapsed >= cooldownInMillis) {
                        teleport(player, location);

                        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    } else {
                        float remainingSeconds = ((float) (cooldownInMillis - timeElapsed)) / 1000.0F;
                        player.sendMessage(String.format(ChatColor.YELLOW + "" + ChatColor.BOLD + "You can't use /warp for another %.1f seconds", remainingSeconds));
                    }
                }
            }

        } catch (SQLException e) {
            player.sendMessage(ChatColor.RED + "Something went wrong while going to the home, please try again. If the error persists, contact an Admin.");

            Okaerinasai.getInstance().getLogger().warning("Failed to teleport " + player.getName() + "|" + player.getUniqueId() + " to " + warpLabel + " warp");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean teleport(Player player, Location location) {
        if (Teleportation.teleportAndPlayEffects(player, location)) {
            player.sendMessage("Okaerinasai, goshujin-sama!!!");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Something went wrong while going to the warp, please try again. If the error persists, contact an Admin.");
            return false;
        }
    }
}
