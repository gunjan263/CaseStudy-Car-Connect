package Main;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Dao.ReservationService;
import Entity.Reservation;
import Exception.DatabaseConnectionException;
import Exception.ReservationException;


public class ReservationServiceMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ReservationService reservationService = new ReservationService();

    public static void showReservationServiceMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nReservation Service Menu");
            System.out.println("1. Get Reservation by ID");
            System.out.println("2. Get Reservations by Customer ID");
            System.out.println("3. Create Reservation");
            System.out.println("4. Update Reservation");
            System.out.println("5. Cancel Reservation");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = ReservationServiceMenu.scanner.nextInt();
            ReservationServiceMenu.scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    getReservationById();
                    break;
                case 2:
                    getReservationsByCustomerId();
                    break;
                case 3:
                    createReservation();
                    break;
                case 4:
                    updateReservation();
                    break;
                case 5:
                    cancelReservation();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }

    private static void getReservationById() {
        System.out.print("Enter Reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            System.out.println("Reservation details:");
            System.out.println(reservation);
        } catch (ReservationException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getReservationsByCustomerId() {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            List<Reservation> reservations = reservationService.getReservationsByCustomerId(customerId);
            System.out.println("Reservations for Customer ID " + customerId + ":");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createReservation() {
        try {
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter Vehicle ID: ");
            int vehicleId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter Start Date (yyyy-MM-dd): ");
            String startDateStr = scanner.nextLine();
            LocalDate startDate = LocalDate.parse(startDateStr);

            System.out.print("Enter End Date (yyyy-MM-dd): ");
            String endDateStr = scanner.nextLine();
            LocalDate endDate = LocalDate.parse(endDateStr);

            System.out.print("Enter Total Cost: ");
            double totalCost = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter Status: ");
            String status = scanner.nextLine();

            Reservation reservation = new Reservation(customerId, vehicleId, startDate, endDate, totalCost, status);
            reservationService.createReservation(reservation);

            System.out.println("Reservation created successfully!");
        } catch (DateTimeParseException | InputMismatchException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static class DateConverter {
        public static Date convertToDate(LocalDate localDate) {
            return java.sql.Date.valueOf(localDate);
        }
    }

    private static void updateReservation() {
        try {
            System.out.print("Enter Reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Reservation existingReservation = reservationService.getReservationById(reservationId);

            // Ask for user input for updated reservation details
            System.out.print("Enter new Start Date (yyyy-MM-dd): ");
            String newStartDateStr = scanner.nextLine();

            System.out.print("Enter new End Date (yyyy-MM-dd): ");
            String newEndDateStr = scanner.nextLine();

            LocalDate newStartDate = LocalDate.parse(newStartDateStr);
            LocalDate newEndDate = LocalDate.parse(newEndDateStr);

            // Convert LocalDate to Date
            Date startDate = DateConverter.convertToDate(newStartDate);
            Date endDate = DateConverter.convertToDate(newEndDate);

            existingReservation.setStartDate(startDate);
            existingReservation.setEndDate(endDate);

            // Update the reservation
            reservationService.updateReservation(existingReservation);
            System.out.println("Reservation updated successfully!");
        } catch (DateTimeParseException | InputMismatchException | DatabaseConnectionException |
                 ReservationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void cancelReservation() {
        try {
            System.out.print("Enter Reservation ID to cancel: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            reservationService.cancelReservation(reservationId);
            System.out.println("Reservation canceled successfully!");
        } catch (InputMismatchException | DatabaseConnectionException | ReservationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
