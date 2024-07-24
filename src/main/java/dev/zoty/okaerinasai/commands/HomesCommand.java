package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.models.HomeModel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class HomesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            try {
                List<String> foundHomes = HomeModel.getHomeLabels(player.getUniqueId().toString());

                if (!foundHomes.isEmpty()) {
                    StringBuilder message = new StringBuilder();
                    message.append("You have ").append(ChatColor.AQUA).append(ChatColor.BOLD).append(foundHomes.size()).append(ChatColor.RESET).append(" homes registered:\n");
                    for (int i = 0; i < foundHomes.size() -1; i++) {
                        String home = foundHomes.get(i);
                        message.append(ChatColor.GOLD).append(ChatColor.BOLD).append(home).append(ChatColor.RESET).append(", ");
                    }
                    message.append(ChatColor.GOLD).append(ChatColor.BOLD).append(foundHomes.getLast()).append(ChatColor.RESET);
                    message.append("Type \"/home <home>\" to teleport to one of your homes.");
                    player.sendMessage(message.toString());
                } else {
                    player.sendMessage(ChatColor.YELLOW + "You have no homes registered");
                }
            } catch (SQLException e) {
                player.sendMessage(ChatColor.RED + "Something went wrong while searching for your homes, please try again. If the error persists, contact an Admin.");

                Okaerinasai.getInstance().getLogger().warning("Failed to get homes for " + player.getName() + " | " + player.getUniqueId());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
