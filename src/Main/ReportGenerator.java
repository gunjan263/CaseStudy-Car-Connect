package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ReportGenerator {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CarRentalDatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gunjan@2001";

    // Other methods and DB connection details...

    public void generateComprehensiveReport() {
        System.out.println("=== Comprehensive Report ===");

        // Generate and display the reservation history report
        reservationHistoryReport();

        // Ask the user to input the start date for the vehicle utilization report
        System.out.print("Enter start date for Vehicle Utilization Report (yyyy-MM-dd HH:mm:ss): ");
        String startDateStr = scanner.nextLine();
        vehicleUtilizationCalculatorReport(startDateStr);

        // Generate and display the revenue report
        revenueCalculatorReport();

        // Other reports or data can be included here as needed
    }

    public void reservationHistoryReport() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Reservation";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Display header or relevant details for reservation history
                System.out.println("Reservation History:");

                // Display each reservation record
                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("ReservationID");
                    int customerID = resultSet.getInt("CustomerID");
                    int vehicleID = resultSet.getInt("VehicleID");

                    // Display or process the retrieved reservation details
                    System.out.println("Reservation ID: " + reservationID);
                    System.out.println("Customer ID: " + customerID);
                    System.out.println("Vehicle ID: " + vehicleID);
                    // Display other relevant details
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vehicleUtilizationCalculatorReport(String startDateStr) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = dateFormat.parse(startDateStr);
            Date currentDate = new Date(); // Get current date and time

            // Fetch reservation information from the start date to the current date
            Map<Integer, Long> vehicleUsageMap = new HashMap<>();

            String sql = "SELECT VehicleID, StartDate, EndDate FROM Reservation WHERE StartDate >= ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, new Timestamp(startDate.getTime()));
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int vehicleID = resultSet.getInt("VehicleID");
                        Timestamp dbStartDate = resultSet.getTimestamp("StartDate");
                        Timestamp dbEndDate = resultSet.getTimestamp("EndDate");

                        // Calculate usage time for each reservation from the start date to the current date
                        long overlapStart = Math.max(startDate.getTime(), dbStartDate.getTime());
                        long overlapEnd = Math.min(currentDate.getTime(), (dbEndDate != null ? dbEndDate.getTime() : currentDate.getTime()));
                        long usageTime = Math.max(0, overlapEnd - overlapStart);

                        vehicleUsageMap.put(vehicleID, vehicleUsageMap.getOrDefault(vehicleID, 0L) + usageTime);
                    }
                }
            }

            // Calculate and display the total usage time for each vehicle
            System.out.println("Vehicle Utilization Report:");
            for (Map.Entry<Integer, Long> entry : vehicleUsageMap.entrySet()) {
                int vehicleID = entry.getKey();
                long totalUsageTime = entry.getValue();

                // Calculate hours, minutes, and seconds from total milliseconds
                long hours = TimeUnit.MILLISECONDS.toHours(totalUsageTime);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(totalUsageTime - TimeUnit.HOURS.toMillis(hours));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(totalUsageTime - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));

                System.out.println("Vehicle ID: " + vehicleID + " - Total Usage: " + hours + "h " + minutes + "m " + seconds + "s");
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void revenueCalculatorReport() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Calculate total revenue
            double totalRevenue = calculateTotalRevenueFromDB(connection);

            // Output the calculated total revenue
            System.out.println("Total Revenue from Database: $" + totalRevenue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double calculateTotalRevenueFromDB(Connection connection) {
        double totalRevenue = 0.0;

        try {
            String sql = "SELECT SUM(TotalCost) AS TotalRevenue FROM Reservation";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalRevenue = resultSet.getDouble("TotalRevenue");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    // Other methods...
}
