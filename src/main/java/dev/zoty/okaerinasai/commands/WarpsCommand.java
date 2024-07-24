package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.models.WarpModel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class WarpsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            try {
                List<String> foundWarps = WarpModel.getWarpLabels();

                if (!foundWarps.isEmpty()) {
                    StringBuilder message = new StringBuilder();
                    message.append(ChatColor.AQUA).append(ChatColor.BOLD).append(foundWarps.size()).append(ChatColor.RESET).append(" warps registered:\n");
                    for (int i = 0; i < foundWarps.size() -1; i++) {
                        String home = foundWarps.get(i);
                        message.append(ChatColor.GOLD).append(ChatColor.BOLD).append(home).append(ChatColor.RESET).append(", ");
                    }
                    message.append(ChatColor.GOLD).append(ChatColor.BOLD).append(foundWarps.getLast()).append(ChatColor.RESET).append("\n");
                    message.append("Type \"/warp <warp>\" to teleport to one of the warps.");
                    player.sendMessage(message.toString());
                } else {
                    player.sendMessage(ChatColor.YELLOW + "There are no registered warps.");
                }
            } catch (SQLException e) {
                player.sendMessage(ChatColor.RED + "Something went wrong while searching for warps, please try again. If the error persists, contact an Admin.");

                Okaerinasai.getInstance().getLogger().warning("Failed to get warps for " + player.getName() + " | " + player.getUniqueId());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
