package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.HomeModel;
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

public class HomeCommand implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return executeCommand(player, command, label, args);
        }
        return true;
    }

    private boolean executeCommand(Player player, Command command, String label, String[] args) {
        String homeLabel;
        if (args.length > 0) {
            homeLabel = args[0];
        } else {
            homeLabel = Settings.getDefaultHome();
        }

        try {
            int homeCount = HomeModel.getNumberOfHomes(player.getUniqueId().toString());

            if (homeCount == 0) {
                player.sendMessage(ChatColor.RED + "You have no homes registered! Use \"/sethome <home>\" to register a new home.");
                return true;
            }

            Location location = HomeModel.getHomeLocation(homeLabel, player.getUniqueId().toString());
            if (location == null) {

            } else {
                if (!this.cooldown.containsKey(player.getUniqueId())) {
                    this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                    teleport(player, location);
                } else {
                    // Calculate difference in ms
                    long timeElapsed = System.currentTimeMillis() - this.cooldown.get(player.getUniqueId());

                    // Gets the config cooldown in seconds and convert to milliseconds
                    long cooldownInMillis = (long) (Settings.getCooldown() * 1000);
                    if (timeElapsed >= cooldownInMillis) {
                        teleport(player, location);

                        this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    } else {
                        float remainingSeconds = ((float) (cooldownInMillis - timeElapsed)) / 1000.0F;
                        player.sendMessage(String.format(ChatColor.YELLOW + "" + ChatColor.BOLD + "You can't use /home for another %.2f seconds", remainingSeconds));
                    }
                }
            }
        } catch (SQLException e) {
            player.sendMessage(ChatColor.RED + "Something went wrong while going to your home, please try again. If the error persists, contact an Admin.");

            Okaerinasai.getInstance().getLogger().warning("Failed to teleport " + player.getName() + "|" + player.getUniqueId() + " to " + homeLabel);
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean teleport(Player player, Location location) {
        if (Teleportation.teleportAndPlayEffects(player, location)) {
            player.sendMessage("Okaerinasai, goshujin sama!!!");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Something went wrong while going to your home, please try again. If the error persists, contact an Admin.");
            return false;
        }
    }
}
