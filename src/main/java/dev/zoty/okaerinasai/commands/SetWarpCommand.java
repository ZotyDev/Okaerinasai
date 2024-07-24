package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.WarpModel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SetWarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            try {
                if (args.length > 0) {
                    return executeCommand(player, args[0]);
                } else {
                    return executeCommand(player, Settings.getDefaultWarp());
                }
            } catch (SQLException e) {
                player.sendMessage(ChatColor.RED + "Something went wrong while setting the warp, please try again. If the error persists, contact an Admin.");

                Okaerinasai.getInstance().getLogger().warning("Failed to set a warp " + player.getName() + " | " + player.getUniqueId());
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean executeCommand(Player player, String label) throws SQLException{
        Location location = player.getLocation();
        WarpModel.insertWarp(
                label,
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        );

        return true;
    }
}
