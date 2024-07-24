package dev.zoty.okaerinasai.models;

import dev.zoty.okaerinasai.Okaerinasai;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeModel {
    public static void insertHome(String label, String uuidPlayer, String dimension, double x, double y, double z) throws SQLException {
        String sql = "INSERT INTO home (label, uuid_player, dimension, x, y, z) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE dimension = VALUES(dimension), x = VALUES(x), y = VALUES(y), z = VALUES(z);";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, label);
        statement.setString(2, uuidPlayer);
        statement.setString(3, dimension);
        statement.setDouble(4, x);
        statement.setDouble(5, y);
        statement.setDouble(6, z);
        statement.executeUpdate();
        statement.close();
    }

    public static void deleteHome(String label, String uuidPlayer) throws SQLException {
        String sql = "DELETE " +
                "FROM home " +
                "WHERE label = ? AND uuid_player = ?";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, label);
        statement.setString(2, uuidPlayer);
        statement.executeUpdate();
        statement.close();
    }

    public static Location getHomeLocation(String label, String uuidPlayer) throws SQLException {
        String sql = "SELECT dimension, x, y, z " +
                "FROM home " +
                "WHERE label = ? AND uuid_player = ?;";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, label);
        statement.setString(2, uuidPlayer);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String dimension = resultSet.getString("dimension");
            double x = resultSet.getDouble("x");
            double y = resultSet.getDouble("y");
            double z = resultSet.getDouble("z");

            World world = Bukkit.getWorld(dimension);
            statement.close();
            return new Location(world, x, y, z);
        } else {
            statement.close();
            return null;
        }
    }

    public static int getNumberOfHomes(String uuidPlayer) throws SQLException {
        String sql = "SELECT COUNT(*) " +
                "FROM home " +
                "WHERE uuid_player = ?;";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, uuidPlayer);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int numberOfHomes = resultSet.getInt(1);
            statement.close();
            return numberOfHomes;
        } else {
            return 0;
        }
    }

    public static List<String> getHomeLabels(String uuidPlayer) throws SQLException {
        String sql = "SELECT label " +
                "FROM home " +
                "WHERE uuid_player = ?;";

        List<String> labels = new ArrayList<>();
        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, uuidPlayer);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            labels.add(resultSet.getString("label"));
        }

        statement.close();
        return labels;
    }
}
