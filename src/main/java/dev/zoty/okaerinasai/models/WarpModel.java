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

public class WarpModel {
    public static void insertWarp(String label, String dimension, double x, double y, double z) throws SQLException {
        String sql = "INSERT INTO warp (label, dimension, x, y, z) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE dimension = VALUES(dimension), x = VALUES(x), y = VALUES(y), z = VALUES(z)";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, label);
        statement.setString(2, dimension);
        statement.setDouble(3, x);
        statement.setDouble(4, y);
        statement.setDouble(5, z);
        statement.executeUpdate();
        statement.close();
    }

    public static void deleteWarp(String label) throws SQLException {
        String sql = "DELETE " +
                "FROM warp " +
                "WHERE label = ?;";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, label);
        statement.executeUpdate();
        statement.close();
    }

    public static Location getWarpLocation(String label) throws SQLException {
        String sql = "SELECT dimension, x, y, z " +
                "FROM warp " +
                "WHERE label = ?;";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        statement.setString(1, label);
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

    public static int getNumberOfWarps() throws SQLException {
        String sql = "SELECT COUNT(*) " +
                "FROM warp;";

        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int numberOfWarps = resultSet.getInt(1);
            statement.close();
            return numberOfWarps;
        } else {
            return 0;
        }
    }

    public static List<String> getWarpLabels() throws SQLException {
        String sql = "SELECT label " +
                "FROM warp;";

        List<String> labels = new ArrayList<>();
        PreparedStatement statement = Okaerinasai.getDatabase().getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            labels.add(resultSet.getString("label"));
        }

        statement.close();
        return labels;
    }
}
