package Dao;

import Entity.Reservation;
import Exception.DatabaseConnectionException;
import Exception.ReservationException;
import Util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ReservationService implements IReservationService {
    @Override
    public Reservation getReservationById(int reservationId) throws ReservationException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Reservation WHERE ReservationID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reservationId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractReservationFromResultSet(resultSet);
                    } else {
                        throw new ReservationException("Reservation with ID " + reservationId + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public List<Reservation> getReservationsByCustomerId(int customerId) throws DatabaseConnectionException {
        List<Reservation> customerReservations = new ArrayList<>();
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Reservation WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Reservation reservation = extractReservationFromResultSet(resultSet);
                        customerReservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
        return customerReservations;
    }

    private Reservation extractReservationFromResultSet(ResultSet resultSet) throws SQLException {
        // Extracts reservation data from the ResultSet and creates a Reservation object
        return new Reservation(
                resultSet.getInt("ReservationID"),
                resultSet.getInt("CustomerID"),
                resultSet.getInt("VehicleID"),
                resultSet.getTimestamp("StartDate"),
                resultSet.getTimestamp("EndDate"),
                resultSet.getDouble("TotalCost"),
                resultSet.getString("Status")
        );
    }

    public void createReservation(Reservation reservationData) throws DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            // Check for existing reservations within the specified time slot
            if (isReservationSlotAvailable(connection, reservationData.getVehicleID(), reservationData.getStartDate(), reservationData.getEndDate())) {
                // If the time slot is available, proceed to create the reservation
                String sql = "INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, reservationData.getCustomerID());
                    statement.setInt(2, reservationData.getVehicleID());
                    statement.setTimestamp(3, new Timestamp(reservationData.getStartDate().getTime()));
                    statement.setTimestamp(4, new Timestamp(reservationData.getEndDate().getTime()));
                    statement.setDouble(5, reservationData.getTotalCost());
                    statement.setString(6, reservationData.getStatus());
                    statement.executeUpdate();
                }
            } else {
                System.out.println("Another reservation already exists for this time slot. Please choose a different time slot or date.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    private boolean isReservationSlotAvailable(Connection connection, int vehicleID, java.util.Date startDate, java.util.Date endDate) throws SQLException {
        String sql = "SELECT * FROM Reservation WHERE VehicleID = ? AND ((StartDate <= ? AND EndDate >= ?) OR (StartDate <= ? AND EndDate >= ?))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleID);
            statement.setTimestamp(2, new Timestamp(startDate.getTime()));
            statement.setTimestamp(3, new Timestamp(startDate.getTime()));
            statement.setTimestamp(4, new Timestamp(endDate.getTime()));
            statement.setTimestamp(5, new Timestamp(endDate.getTime()));
            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next(); // If no overlapping reservation found, the slot is available
            }
        }
    }


    private boolean isReservationSlotAvailable(Connection connection, int vehicleID, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT * FROM Reservation WHERE VehicleID = ? AND ((StartDate <= ? AND EndDate >= ?) OR (StartDate <= ? AND EndDate >= ?))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleID);
            statement.setTimestamp(2, new Timestamp(startDate.getTime()));
            statement.setTimestamp(3, new Timestamp(startDate.getTime()));
            statement.setTimestamp(4, new Timestamp(endDate.getTime()));
            statement.setTimestamp(5, new Timestamp(endDate.getTime()));
            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next(); // If no overlapping reservation found, the slot is available
            }
        }
    }


    @Override
    public void updateReservation(Reservation reservationData) throws ReservationException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "UPDATE Reservation SET CustomerID = ?, VehicleID = ?, StartDate = ?, EndDate = ?, TotalCost = ?, Status = ? WHERE ReservationID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reservationData.getCustomerID());
                statement.setInt(2, reservationData.getVehicleID());
                statement.setTimestamp(3, new Timestamp(reservationData.getStartDate().getTime()));
                statement.setTimestamp(4, new Timestamp(reservationData.getEndDate().getTime()));
                statement.setDouble(5, reservationData.getTotalCost());
                statement.setString(6, reservationData.getStatus());
                statement.setInt(7, reservationData.getReservationID());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new ReservationException("Reservation with ID " + reservationData.getReservationID() + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    @Override
    public void cancelReservation(int reservationId) throws ReservationException, DatabaseConnectionException {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "DELETE FROM Reservation WHERE ReservationID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reservationId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new ReservationException("Reservation with ID " + reservationId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }

    public static double calculateCost() throws DatabaseConnectionException {
        double totalSum = 0.0;

        try (Connection connection = DBConnUtil.getConnection()) {
            String query = "SELECT TotalCost FROM Reservation";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        totalSum += resultSet.getDouble("TotalCost");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }

        return totalSum;
    }


}
