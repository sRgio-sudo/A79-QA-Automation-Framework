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
}

