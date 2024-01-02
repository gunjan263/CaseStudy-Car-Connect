package Main;

import Util.DBConnUtil;

import java.sql.Connection;
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


    public void generateComprehensiveReport() {
        System.out.println("=== Comprehensive Report ===");

        reservationHistoryReport();

        // Generate and display the revenue report
        revenueCalculatorReport();
    }

    public void reservationHistoryReport() {
        try (Connection connection = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Reservation";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Display header
                System.out.println("Reservation History:");

                // Display each reservation record
                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("ReservationID");
                    int customerID = resultSet.getInt("CustomerID");
                    int vehicleID = resultSet.getInt("VehicleID");

                    // Display
                    System.out.println("Reservation ID: " + reservationID);
                    System.out.println("Customer ID: " + customerID);
                    System.out.println("Vehicle ID: " + vehicleID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void revenueCalculatorReport() {
        try (Connection connection = DBConnUtil.getConnection()) {
            // Calculate total revenue
            double totalRevenue = calculateTotalRevenueFromDB(connection);

            // Output the calculated total revenue
            System.out.println("Total Revenue from Database: Rs." + totalRevenue);
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
}
