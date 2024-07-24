package dev.zoty.okaerinasai;

import dev.zoty.okaerinasai.commands.DelHomeCommand;
import dev.zoty.okaerinasai.commands.HomeCommand;
import dev.zoty.okaerinasai.commands.HomesCommand;
import dev.zoty.okaerinasai.commands.SetHomeCommand;
import dev.zoty.okaerinasai.db.Database;
import dev.zoty.okaerinasai.listeners.PlayerJoinListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Okaerinasai extends JavaPlugin {
    private static Okaerinasai instance;
    private static Database database;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        database = new Database();
        try {
            database.initializeDatabase();
        } catch (SQLException e) {
            getLogger().warning("Could not connect to/initialize database!");

            e.printStackTrace();
        }

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);

        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("homes").setExecutor(new HomesCommand());

        getLogger().info("Successfully enabled!");
    }

    public static Okaerinasai getInstance() {
        return instance;
    }

    public static Database getDatabase() {
        return database;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
