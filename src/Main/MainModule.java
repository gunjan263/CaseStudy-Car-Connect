package Main;
import java.util.Scanner;

public class MainModule {

    private static boolean isAuthenticated = false;
    private static String currentUserType = "";
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!isAuthenticated) {
                authenticateUser(scanner);
            } else {
                if (currentUserType.equals("Customer")) {
                    customerAccessMenu(scanner);
                } else if (currentUserType.equals("Admin")) {
                    adminAccessMenu(scanner);
                }
            }

            System.out.println("Do you want to exit? (yes/no)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
    }

    private static void authenticateUser(Scanner scanner) {
        System.out.println("Are you a Customer or an Admin?");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        System.out.print("Enter your choice: ");

        int userTypeChoice = Integer.parseInt(scanner.nextLine());

        AuthenticationHandler authHandler = new AuthenticationHandler();
        boolean isAuthenticated = false;

        switch (userTypeChoice) {
            case 1:
                isAuthenticated = authenticateCustomer(scanner, authHandler);
                if (isAuthenticated) {
                    currentUserType = "Customer";
                } else {
                    System.out.println("Access denied for the customer.");
                }
                break;
            case 2:
                isAuthenticated = authenticateAdmin(scanner, authHandler);
                if (isAuthenticated) {
                    currentUserType = "Admin";
                } else {
                    System.out.println("Access denied for the admin.");
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }

        MainModule.isAuthenticated = isAuthenticated;
    }
    private static boolean authenticateCustomer(Scanner scanner, AuthenticationHandler authHandler) {
        System.out.println("Customer Login");

        int attempts = 0;
        boolean isAuthenticated = false;

        while (attempts < AuthenticationHandler.MAX_ATTEMPTS) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            isAuthenticated = authHandler.authenticateCustomer(username, password);

            if (isAuthenticated) {
                System.out.println("Authentication successful! Welcome, " + username + "!");
                break;
            } else {
                attempts++;
                if (attempts < AuthenticationHandler.MAX_ATTEMPTS) {
                    System.out.println("Authentication failed. Invalid credentials. Attempts left: " + (AuthenticationHandler.MAX_ATTEMPTS - attempts));
                } else {
                    System.out.println("Maximum attempts reached. Access denied.");
                }
            }
        }
        return isAuthenticated; // Return the authentication status outside the loop
    }

    private static boolean authenticateAdmin(Scanner scanner, AuthenticationHandler authHandler) {
        System.out.println("Admin Login");

        int attempts = 0;
        boolean isAuthenticated = false;


        while (attempts < AuthenticationHandler.MAX_ATTEMPTS) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            isAuthenticated = authHandler.authenticateAdmin(username, password);

            if (isAuthenticated) {
                System.out.println("Authentication successful! Welcome, " + username + "!");
                break;
            } else {
                attempts++;
                if (attempts < AuthenticationHandler.MAX_ATTEMPTS) {
                    System.out.println("Authentication failed. Invalid credentials. Attempts left: " + (AuthenticationHandler.MAX_ATTEMPTS - attempts));
                } else {
                    System.out.println("Maximum attempts reached. Access denied.");
                }
            }
        }

        return isAuthenticated;
    }

    private static void customerAccessMenu(Scanner scanner) {
        System.out.println("Customer Access Menu:");
        System.out.println("1. Vehicle Service");
        System.out.println("2. Reservation Service");
        System.out.println("Enter your choice:");



        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                VehicleServiceMenu.showVehicleServiceMenu(scanner, true);
                break;
            case 2:
                ReservationServiceMenu.showReservationServiceMenu(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void adminAccessMenu(Scanner scanner) {
        System.out.println("Admin Access Menu:");
        System.out.println("1. Customer Service");
        System.out.println("2. Vehicle Service");
        System.out.println("3. Reservation Service");
        System.out.println("4. Admin Service");
        System.out.println("5. Report Generation");
        System.out.println("Enter your choice:");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                CustomerServiceMenu.showCustomerServiceMenu(scanner);
                break;
            case 2:
                VehicleServiceMenu.showVehicleServiceMenu(scanner, false);
                break;
            case 3:
                ReservationServiceMenu.showReservationServiceMenu(scanner);
                break;
            case 4:
                AdminServiceMenu.showAdminServiceMenu(scanner);
                break;
            case 5:
                generateReport();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void generateReport() {
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateComprehensiveReport();
    }
}
