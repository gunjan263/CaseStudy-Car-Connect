package Main;

import Dao.VehicleService;
import Entity.Vehicle;
import Exception.DatabaseConnectionException;
import Exception.VehicleNotFoundException;
import java.util.List;
import java.util.Scanner;

public class VehicleServiceMenu {
    private static final VehicleService vehicleService = new VehicleService();

    public static void showVehicleServiceMenu(Scanner scanner, boolean customerAccess) {

        System.out.println("Vehicle Service Menu");
        System.out.println("1. Get Available Vehicles");
        System.out.println("2. Get All Vehicles");

        if (!customerAccess) {
            System.out.println("3. Get Vehicle by ID");
            System.out.println("4. Add Vehicle");
            System.out.println("5. Update Vehicle");
            System.out.println("6. Remove Vehicle");
        }

        System.out.println("Enter your choice:");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                getAvailableVehicles();
                break;
            case 2:
                getAllVehicles();
                break;
            case 3:
                if (!customerAccess) {
                    getVehicleById(scanner);
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            case 4:
                if (!customerAccess) {
                    addVehicle(scanner);
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            case 5:
                if (!customerAccess) {
                    updateVehicle(scanner);
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            case 6:
                if (!customerAccess) {
                    removeVehicle(scanner);
                } else {
                    System.out.println("Invalid choice!");
                }
                break;

            default:
                System.out.println("Invalid choice!");
        }
    }
    private static void getVehicleById(Scanner scanner) {
        System.out.println("Enter Vehicle ID:");
        int vehicleId = Integer.parseInt(scanner.nextLine());

        try {
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle != null) {
                System.out.println("Vehicle details:");
                System.out.println(vehicle);
            } else {
                System.out.println("Vehicle not found with ID: " + vehicleId);
            }
        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getAvailableVehicles() {
        try {
            List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
            if (!availableVehicles.isEmpty()) {
                System.out.println("Available Vehicles:");
                for (Vehicle vehicle : availableVehicles) {
                    System.out.println(vehicle);
                }
            } else {
                System.out.println("No available vehicles.");
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addVehicle(Scanner scanner) {
        try {
            System.out.println("Enter Vehicle Model:");
            String model = scanner.nextLine();

            System.out.println("Enter Vehicle Make:");
            String make = scanner.nextLine();

            System.out.println("Enter Vehicle Year:");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Vehicle Color:");
            String color = scanner.nextLine();

            System.out.println("Enter Vehicle Registration Number:");
            String registrationNumber = scanner.nextLine();

            System.out.println("Enter Vehicle Daily Rate:");
            double dailyRate = Double.parseDouble(scanner.nextLine());

            boolean availability = true;

            // Create a new Vehicle object
            Vehicle newVehicle = new Vehicle(0, model, make, year, color, registrationNumber, availability, dailyRate);
            vehicleService.addVehicle(newVehicle);

            System.out.println("Vehicle added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateVehicle(Scanner scanner) {
            System.out.println("Enter Vehicle ID to update:");
            int vehicleId = Integer.parseInt(scanner.nextLine());

            try {
                Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);

                if (existingVehicle != null) {
                    System.out.println("Enter Model Name:");
                    String model = scanner.nextLine();

                    System.out.println("Enter Make Name:");
                    String make = scanner.nextLine();

                    System.out.println("Enter Year Purchased:");
                    int year = scanner.nextInt();

                    System.out.println("Enter Colour :");
                    String colour = scanner.nextLine();

                    System.out.println("Enter Registration Number:");
                    String registrationNumber = scanner.nextLine();

                    System.out.println("Enter Availability:");
                    boolean availability = scanner.nextBoolean();

                    System.out.println("Enter Daily Rate:");
                    int dailyRate = scanner.nextInt();

                    existingVehicle.setModel(model);
                    existingVehicle.setMake(make);
                    existingVehicle.setYear(year);
                    existingVehicle.setColor(colour);
                    existingVehicle.setRegistrationNumber(registrationNumber);
                    existingVehicle.setAvailable(availability);
                    existingVehicle.setDailyRate(dailyRate);

                    vehicleService.updateVehicle(existingVehicle);
                    System.out.println("Customer details updated successfully!");
                } else {
                    System.out.println("Customer not found with ID: " + vehicleId);
                }
            } catch (DatabaseConnectionException | VehicleNotFoundException e) {
                System.out.println("Error connecting to the database: " + e.getMessage());
            }
        }

        private static void removeVehicle(Scanner scanner) {
        try {
            System.out.println("Enter Vehicle ID to remove:");
            int vehicleId = Integer.parseInt(scanner.nextLine());
            vehicleService.removeVehicle(vehicleId);
            System.out.println("Vehicle removed successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error occurred while removing the vehicle: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    public static void getAllVehicles() {
        try {
            List<Vehicle> allVehicles = vehicleService.getAllVehicles();
            if (!allVehicles.isEmpty()) {
                System.out.println("All Vehicles:");
                for (Vehicle vehicle : allVehicles) {
                    System.out.println(vehicle);
                }
            } else {
                System.out.println("No vehicles found.");
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
