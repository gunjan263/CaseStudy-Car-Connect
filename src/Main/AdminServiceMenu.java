package Main;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Dao.AdminService;
import Entity.Admin;
import Exception.AdminNotFoundException;
import Exception.DatabaseConnectionException;

public class AdminServiceMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminService adminService = new AdminService();

    public static void showAdminServiceMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Service Menu");
            System.out.println("1. Get Admin by ID");
            System.out.println("2. Get Admin by Username");
            System.out.println("3. Register Admin");
            System.out.println("4. Update Admin");
            System.out.println("5. Delete Admin");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = AdminServiceMenu.scanner.nextInt();
            AdminServiceMenu.scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    getAdminById();
                    break;
                case 2:
                    getAdminByUsername();
                    break;
                case 3:
                    registerAdmin();
                    break;
                case 4:
                    updateAdmin();
                    break;
                case 5:
                    deleteAdmin();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }

    private static void getAdminById() {
        System.out.print("Enter Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Admin admin = adminService.getAdminById(adminId);
            System.out.println("Admin details:");
            System.out.println(admin);
        } catch (AdminNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getAdminByUsername() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();

        try {
            Admin admin = adminService.getAdminByUsername(username);
            System.out.println("Admin details:");
            System.out.println(admin);
        } catch (AdminNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerAdmin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Enter role: ");
        String role = scanner.nextLine();

        System.out.println("Enter Join Date (dd-MM-yyyy):");
        String joinDate = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date joiningDate;
        try {
            joiningDate = dateFormat.parse(joinDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Using current date instead.");
            joiningDate = new java.util.Date();
        }


//        LocalDate joinDate = LocalDate.now();
//        // Create an Admin object with the obtained details
       Admin admin = new Admin(firstName, lastName, email, phoneNumber, username, password, role, joiningDate);


        try {
            adminService.registerAdmin(admin);
            System.out.println("Admin registered successfully!");
        } catch (DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    private static void updateAdmin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Admin existingAdmin = adminService.getAdminById(adminId);

            System.out.println("Enter new first name: ");
            String newFirstName = scanner.nextLine();
            existingAdmin.setFirstName(newFirstName);

            System.out.println("Enter new last name: ");
            String newLastName = scanner.nextLine();
            existingAdmin.setLastName(newLastName);

            System.out.println("Enter new email: ");
            String newEmail = scanner.nextLine();
            existingAdmin.setEmail(newEmail);

            System.out.println("Enter new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            existingAdmin.setPhoneNumber(newPhoneNumber);

            System.out.println("Enter new username: ");
            String newUsername = scanner.nextLine();
            existingAdmin.setUsername(newUsername);

            System.out.println("Enter new password: ");
            String newPassword = scanner.nextLine();
            existingAdmin.setPassword(newPassword);

            System.out.println("Enter new role: ");
            String newRole = scanner.nextLine();
            existingAdmin.setRole(newRole);

            System.out.println("Enter new join date (YYYY-MM-DD): ");
            String newJoinDateStr = scanner.nextLine();
            LocalDate newJoinDate = LocalDate.parse(newJoinDateStr);

            // Convert LocalDate to Date
            Date convertedJoinDate = java.sql.Date.valueOf(newJoinDate);

            existingAdmin.setJoinDate(convertedJoinDate);

            // Update admin details
            adminService.updateAdmin(existingAdmin);
            System.out.println("Admin updated successfully!");
        } catch (AdminNotFoundException | DatabaseConnectionException | DateTimeParseException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }



    private static void deleteAdmin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            adminService.deleteAdmin(adminId);
            System.out.println("Admin deleted successfully!");
        } catch (AdminNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }


    public static void main(String[] args) {
        showAdminServiceMenu(scanner);
    }
}
