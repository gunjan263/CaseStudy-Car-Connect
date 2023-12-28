package Dao;

import Entity.Admin;
import Exception.AdminNotFoundException;
import Exception.DatabaseConnectionException;
import Util.DBConnUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService implements IAdminService {

    @Override
    public Admin getAdminById(int adminId) throws AdminNotFoundException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Admin WHERE AdminID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, adminId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractAdminFromResultSet(resultSet);
                    } else {
                        throw new AdminNotFoundException("Admin with ID " + adminId + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public Admin getAdminByUsername(String username) throws AdminNotFoundException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Admin WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractAdminFromResultSet(resultSet);
                    } else {
                        throw new AdminNotFoundException("Admin with username " + username + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void registerAdmin(Admin adminData) throws DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO Admin (FirstName, LastName, Email, PhoneNumber, Username, Password, Role, JoinDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, adminData.getFirstName());
                statement.setString(2, adminData.getLastName());
                statement.setString(3, adminData.getEmail());
                statement.setString(4, adminData.getPhoneNumber());
                statement.setString(5, adminData.getUsername());
                statement.setString(6, adminData.getPassword());
                statement.setString(7, adminData.getRole());
                statement.setDate(8, new java.sql.Date(adminData.getJoinDate().getTime()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void updateAdmin(Admin adminData) throws AdminNotFoundException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "UPDATE Admin SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, Username = ?, Password = ?, Role = ?, JoinDate = ? WHERE AdminID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, adminData.getFirstName());
                statement.setString(2, adminData.getLastName());
                statement.setString(3, adminData.getEmail());
                statement.setString(4, adminData.getPhoneNumber());
                statement.setString(5, adminData.getUsername());
                statement.setString(6, adminData.getPassword());
                statement.setString(7, adminData.getRole());
                statement.setDate(8, new java.sql.Date(adminData.getJoinDate().getTime()));
                statement.setInt(9, adminData.getAdminID());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new AdminNotFoundException("Admin with ID " + adminData.getAdminID() + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void deleteAdmin(int adminId) throws AdminNotFoundException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "DELETE FROM Admin WHERE AdminID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, adminId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new AdminNotFoundException("Admin with ID " + adminId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }


    private Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        // Extracts admin data from the ResultSet and creates an Admin object
        return new Admin(
                resultSet.getInt("AdminID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"),
                resultSet.getString("Email"),
                resultSet.getString("PhoneNumber"),
                resultSet.getString("Username"),
                resultSet.getString("Password"),
                resultSet.getString("Role"),
                resultSet.getDate("JoinDate")
        );
    }

    // Additional CRUD methods for admins can be implemented here
    // ...
}
