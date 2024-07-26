package dev.zoty.okaerinasai.db;

import dev.zoty.okaerinasai.Okaerinasai;
import dev.zoty.okaerinasai.Settings;
import dev.zoty.okaerinasai.models.HomeModel;
import dev.zoty.okaerinasai.models.PlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (this.connection != null) {
            return this.connection;
        }

        String url = Settings.getDbUrl();
        String user = Settings.getDbUser();
        String password = Settings.getDbPassword();

        this.connection = DriverManager.getConnection(url, user, password);

        Okaerinasai.getInstance().getLogger().info("Successfully connected to database!");

        return this.connection;
    }

    // Initializes the database by making sure all required tables exist.
    public void initializeDatabase() throws SQLException {
        Statement statement = this.getConnection().createStatement();
        String sqlPlayer = "CREATE TABLE IF NOT EXISTS player (" +
                "uuid VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "nickname VARCHAR(46) NOT NULL)";

        String sqlHome = "CREATE TABLE IF NOT EXISTS home (" +
                "label VARCHAR(32) NOT NULL, " +
                "uuid_player VARCHAR(36) NOT NULL, " +
                "dimension VARCHAR(128) NOT NULL, " +
                "x DOUBLE NOT NULL, " +
                "y DOUBLE NOT NULL, " +
                "z DOUBLE NOT NULL, " +
                "PRIMARY KEY (label, uuid_player), " +
                "FOREIGN KEY (uuid_player) REFERENCES player(uuid))";

        String sqlWarp = "CREATE TABLE IF NOT EXISTS warp (" +
                "label VARCHAR(32) NOT NULL PRIMARY KEY, " +
                "dimension VARCHAR(128) NOT NULL, " +
                "x DOUBLE NOT NULL, " +
                "y DOUBLE NOT NULL, " +
                "z DOUBLE NOT NULL)";

        statement.execute(sqlPlayer);
        statement.execute(sqlHome);
        statement.execute(sqlWarp);
        statement.close();

        Okaerinasai.getInstance().getLogger().info("Successfully initialized database!");
    }
}
