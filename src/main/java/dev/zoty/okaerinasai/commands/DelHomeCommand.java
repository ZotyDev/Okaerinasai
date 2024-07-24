package dev.zoty.okaerinasai.commands;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.HomeModel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class DelHomeCommand implements CommandExecutor {
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

            HomeModel.deleteHome(homeLabel, player.getUniqueId().toString());
        } catch (SQLException e) {
            player.sendMessage(ChatColor.RED + "Something went wrong while deleting your home, please try again. If the error persists, contact an Admin.");

            Okaerinasai.getInstance().getLogger().warning("Failed to delete " + homeLabel + " from " + player.getName() + "|" + player.getUniqueId());
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
