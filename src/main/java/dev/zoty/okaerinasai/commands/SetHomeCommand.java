package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.HomeModel;
import dev.zoty.okaerinasai.models.PlayerModel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            try {
                if (args.length > 0) {
                    executeCommand(player, args[0]);
                } else {
                    executeCommand(player, Settings.getDefaultHome());
                }
            } catch (SQLException e) {
                player.sendMessage(ChatColor.RED + "Something went wrong while setting your, please try again. If the error persists, contact an Admin.");

                Okaerinasai.getInstance().getLogger().warning("Failed to set a home for " + player.getName() + " | " + player.getUniqueId());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private boolean executeCommand(Player player, String label) throws SQLException {
        Location location = player.getLocation();
        HomeModel.insertHome(
                label,
                player.getUniqueId().toString(),
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        );

        return true;
    }

}
