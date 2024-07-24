package dev.zoty.okaerinasai.listeners;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.models.PlayerModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        try {
            PlayerModel.insertPlayer(player.getUniqueId().toString(), player.getName());
        } catch (SQLException e) {
            Okaerinasai.getInstance().getLogger().warning("Failed to register player into database!");
            e.printStackTrace();
        }
    }
}
