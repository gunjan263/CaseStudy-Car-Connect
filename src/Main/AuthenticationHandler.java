package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CarRentalDatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gunjan@2001";
    public static final int MAX_ATTEMPTS = 3;

    public static boolean authenticateCustomer(String username, String password) {
        boolean isAuthenticated = false;
        int attempts = 0;

        while (true) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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

            if (isAuthenticated) {
                System.out.println("Authentication successful! Welcome, " + username + "!");
                break;
            } else {
                attempts++;
                if (attempts < AuthenticationHandler.MAX_ATTEMPTS) {
                    System.out.println("Authentication failed. Invalid credentials. Attempts left: " + (AuthenticationHandler.MAX_ATTEMPTS - attempts));
                } else {
                    System.out.println("Maximum attempts reached. Access denied.");
                    break;
                }
            }
        }

        return isAuthenticated;
    }


    public boolean authenticateAdmin(String username, String password) {
        boolean isAuthenticated = false;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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