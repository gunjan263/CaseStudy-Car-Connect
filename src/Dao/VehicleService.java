package Dao;


import Entity.Vehicle;
import Exception.DatabaseConnectionException;
import Exception.VehicleNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleService implements IVehicleService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CarRentalDatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gunjan@2001";

    @Override
    public Vehicle getVehicleById(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Vehicle WHERE VehicleID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, vehicleId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractVehicleFromResultSet(resultSet);
                    } else {
                        throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public List<Vehicle> getAvailableVehicles() throws DatabaseConnectionException {
        List<Vehicle> availableVehicles = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Vehicle WHERE Availability = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setBoolean(1, true);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Vehicle vehicle = extractVehicleFromResultSet(resultSet);
                        availableVehicles.add(vehicle);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
        return availableVehicles;
    }

    @Override
    public void addVehicle(Vehicle vehicleData) throws DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vehicleData.getModel());
                statement.setString(2, vehicleData.getMake());
                statement.setInt(3, vehicleData.getYear());
                statement.setString(4, vehicleData.getColor());
                statement.setString(5, vehicleData.getRegistrationNumber());
                statement.setBoolean(6, vehicleData.isAvailable());
                statement.setDouble(7, vehicleData.getDailyRate());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    throw new SQLException("Adding vehicle failed, no rows affected.");
                }
                else{
                    System.out.println("Update Successful!!");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicleData) throws VehicleNotFoundException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE Vehicle SET Model = ?, Make = ?, Year = ?, Color = ?, RegistrationNumber = ?, Availability = ?, DailyRate = ? WHERE VehicleID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vehicleData.getModel());
                statement.setString(2, vehicleData.getMake());
                statement.setInt(3, vehicleData.getYear());
                statement.setString(4, vehicleData.getColor());
                statement.setString(5, vehicleData.getRegistrationNumber());
                statement.setBoolean(6, vehicleData.isAvailable());
                statement.setDouble(7, vehicleData.getDailyRate());
                statement.setInt(8, vehicleData.getVehicleID());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new VehicleNotFoundException("Vehicle with ID " + vehicleData.getVehicleID() + " not found.");
                }else{
                    System.out.println("Update Successful!!");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void removeVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM Vehicle WHERE VehicleID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, vehicleId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    private Vehicle extractVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        // Extracts vehicle data from the ResultSet and creates a Vehicle object
        return new Vehicle(
                resultSet.getInt("VehicleID"),
                resultSet.getString("Model"),
                resultSet.getString("Make"),
                resultSet.getInt("Year"),
                resultSet.getString("Color"),
                resultSet.getString("RegistrationNumber"),
                resultSet.getBoolean("Availability"),
                resultSet.getDouble("DailyRate")
        );
    }

    public int getVehicleIdByRegistrationNumber(String registrationNumber) {
        int vehicleId = -1; // Default value if ID is not found or an error occurs

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT VehicleID FROM Vehicles WHERE RegistrationNumber = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, registrationNumber);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        vehicleId = resultSet.getInt("VehicleID");
                    }
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return vehicleId;
    }

    // Additional CRUD methods for vehicles can be implemented here
    // ...
}
