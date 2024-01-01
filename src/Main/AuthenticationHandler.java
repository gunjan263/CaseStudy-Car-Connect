package Main;

import Util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationHandler {

    public static final int MAX_ATTEMPTS = 3;

    public static boolean authenticateCustomer(String username, String password) {
        boolean isAuthenticated = false;

        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE Username = ? AND Password = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    isAuthenticated = resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }

    public boolean authenticateAdmin(String username, String password) {
        boolean isAuthenticated = false;

        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Admin WHERE Username = ? AND Password = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    isAuthenticated = resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }

}