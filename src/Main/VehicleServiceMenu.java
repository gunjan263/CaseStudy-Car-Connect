package Main;

import Dao.VehicleService;
import Entity.Vehicle;
import Exception.DatabaseConnectionException;
import Exception.VehicleNotFoundException;

import java.util.List;
import java.util.Scanner;

public class VehicleServiceMenu {
    private static final VehicleService vehicleService = new VehicleService();

    public static void showVehicleServiceMenu(Scanner scanner) {
        System.out.println("Vehicle Service Menu");
        System.out.println("1. Get Vehicle by ID");
        System.out.println("2. Get Available Vehicles");
        System.out.println("3. Add Vehicle");
        System.out.println("4. Update Vehicle");
        System.out.println("5. Remove Vehicle");
        System.out.println("Enter your choice:");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                getVehicleById(scanner);
                break;
            case 2:
                getAvailableVehicles();
                break;
            case 3:
                addVehicle(scanner);
                break;
            case 4:
                updateVehicle(scanner);
                break;
            case 5:
                removeVehicle(scanner);
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

    public static List<Vehicle> getAvailableVehiclesn() {
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
        return null;
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

            // For simplicity, setting availability to true for a newly added vehicle
            boolean availability = true;

            // Create a new Vehicle object
            Vehicle newVehicle = new Vehicle(0, model, make, year, color, registrationNumber, availability, dailyRate);

            System.out.println("Vehicle added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateVehicle(Scanner scanner) {
        try {
            System.out.println("Enter Vehicle ID to update:");
            int vehicleId = Integer.parseInt(scanner.nextLine());

            // Retrieve the vehicle using the ID
            Vehicle existingVehicle = vehicleService.getVehicleById(vehicleId);

            if (existingVehicle != null) {
                // Gather updated details from user input and update the existing vehicle
                // Similar to the addVehicle method, take user input and update the vehicle attributes

                // vehicleService.updateVehicle(existingVehicle); // Uncomment this line once you have implemented the updateVehicle method
                System.out.println("Vehicle details updated successfully.");
            } else {
                System.out.println("Vehicle not found with ID: " + vehicleId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
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

}
