package dev.zoty.okaerinasai.models;


import dev.zoty.okaerinasai.Okaerinasai;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerModel {
    // Insert a player into the database. If the player changes its name nothing breaks because it will just get
    // updated in the database.
    public static void insertPlayer(String uuid, String nickname) throws SQLException {
        String sql = "INSERT INTO player (uuid, nickname) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE nickname = VALUES(nickname);";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, uuid);
        statement.setString(2, nickname);
        statement.executeUpdate();
        statement.close();
    }
}
