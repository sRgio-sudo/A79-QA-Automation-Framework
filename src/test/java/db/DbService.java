package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbService {

    public String getUserPasswordHash(String email) {
        String query = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement ps = DbConnectionManager
                .getConnection()
                .prepareStatement(query)) {

            ps.setString(1, email);
            try
                    (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user password", e);
        }
        return null;
    }
    public String checkPlaylistInBase(String playListName) {
        String query = "SELECT name FROM playlists WHERE name = ? ORDER BY created_at DESC";
        try (PreparedStatement ps = DbConnectionManager
                .getConnection()
                .prepareStatement(query)) {

            ps.setString(1, playListName);
            try
                    (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user palylist", e);
        }
        return null;
    }
}

