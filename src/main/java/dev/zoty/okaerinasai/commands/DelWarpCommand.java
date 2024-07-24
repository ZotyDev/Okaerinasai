package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.WarpModel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class DelWarpCommand implements CommandExecutor {
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

            WarpModel.deleteWarp(warpLabel);
        } catch (SQLException e) {
            player.sendMessage(ChatColor.RED + "Something went wrong while deleting the warp, please try again. If the error persists, contact an Admin.");

            Okaerinasai.getInstance().getLogger().warning("Failed to delete " + warpLabel);
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
