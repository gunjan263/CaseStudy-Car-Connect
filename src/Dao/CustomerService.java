package Dao;


import Entity.Customer;
import Exception.AuthenticationException;
import Exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService implements ICustomerService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CarRentalDatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gunjan@2001";

    @Override
    public Customer getCustomerById(int customerId) throws AuthenticationException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Customer WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractCustomerFromResultSet(resultSet);
                    } else {
                        throw new AuthenticationException("Customer with ID " + customerId + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomerByUsername(String username) throws AuthenticationException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Customer WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractCustomerFromResultSet(resultSet);
                    } else {
                        throw new AuthenticationException("Customer with username " + username + " not found.");
                    }
                } catch (AuthenticationException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    // Implement other methods for RegisterCustomer, UpdateCustomer, DeleteCustomer, etc.
    // ...

    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        // Extracts customer data from the ResultSet and creates a Customer object
        return new Customer(
                resultSet.getInt("CustomerID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"),
                resultSet.getString("Email"),
                resultSet.getString("PhoneNumber"),
                resultSet.getString("Address"),
                resultSet.getString("Username"),
                resultSet.getString("Password"),
                resultSet.getDate("RegistrationDate")
        );
    }

    public void registerCustomer(Customer customerData) throws DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber, Address, Username, Password, RegistrationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customerData.getFirstName());
                statement.setString(2, customerData.getLastName());
                statement.setString(3, customerData.getEmail());
                statement.setString(4, customerData.getPhoneNumber());
                statement.setString(5, customerData.getAddress());
                statement.setString(6, customerData.getUsername());
                statement.setString(7, customerData.getPassword());
                statement.setDate(8, new java.sql.Date(customerData.getRegistrationDate().getTime()));

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void updateCustomer(Customer customerData) throws AuthenticationException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE Customer SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, Address = ?, Username = ?, Password = ?, RegistrationDate = ? WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customerData.getFirstName());
                statement.setString(2, customerData.getLastName());
                statement.setString(3, customerData.getEmail());
                statement.setString(4, customerData.getPhoneNumber());
                statement.setString(5, customerData.getAddress());
                statement.setString(6, customerData.getUsername());
                statement.setString(7, customerData.getPassword());
                statement.setDate(8, new java.sql.Date(customerData.getRegistrationDate().getTime()));
                statement.setInt(9, customerData.getCustomerID());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new AuthenticationException("Customer with ID " + customerData.getCustomerID() + " not found.");
                }
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws AuthenticationException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM Customer WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new AuthenticationException("Customer with ID " + customerId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }
}

