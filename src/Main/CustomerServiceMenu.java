package Main;

import Dao.CustomerService;
import Entity.Customer;
import Exception.AuthenticationException;
import Exception.DatabaseConnectionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CustomerServiceMenu {
    private static final CustomerService customerService = new CustomerService();

    public static void showCustomerServiceMenu(Scanner scanner) {
        System.out.println("Customer Service Menu");
        System.out.println("1. Register Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Get Customer by ID");
        System.out.println("4. Get Customer by Username");
        System.out.println("5. Delete Customer");
        System.out.println("Enter your choice:");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                registerCustomer(scanner);
                break;
            case 2:
                updateCustomer(scanner);
                break;
            case 3:
                getCustomerById(scanner);
                break;
            case 4:
                getCustomerByUsername(scanner);
                break;
            case 5:
                deleteCustomer(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("Enter Customer First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Customer Last Name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter Customer Email:");
        String email = scanner.nextLine();

        System.out.println("Enter Customer Phone Number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter Customer Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Customer Username:");
        String username = scanner.nextLine();

        System.out.println("Enter Customer Password:");
        String password = scanner.nextLine();

        System.out.println("Enter Registration Date (dd-MM-yyyy):");
        String regDateStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date registrationDate;
        try {
            registrationDate = dateFormat.parse(regDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Using current date instead.");
            registrationDate = new Date();
        }

        Customer newCustomer = new Customer(0, firstName, lastName, email, phoneNumber, address, username, password, registrationDate);
        try {
            customerService.registerCustomer(newCustomer);
            System.out.println("Customer registered successfully!");
        } catch (DatabaseConnectionException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.println("Enter Customer ID to update:");
        int customerId = Integer.parseInt(scanner.nextLine());

        try {
            Customer existingCustomer = customerService.getCustomerById(customerId);

            if (existingCustomer != null) {
                System.out.println("Enter Customer First Name:");
                String firstName = scanner.nextLine();

                System.out.println("Enter Customer Last Name:");
                String lastName = scanner.nextLine();

                System.out.println("Enter Customer Email:");
                String email = scanner.nextLine();

                // Update customer details
                existingCustomer.setFirstName(firstName);
                existingCustomer.setLastName(lastName);
                existingCustomer.setEmail(email);

                customerService.updateCustomer(existingCustomer);
                System.out.println("Customer details updated successfully!");
            } else {
                System.out.println("Customer not found with ID: " + customerId);
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getCustomerById(Scanner scanner) {
        System.out.println("Enter Customer ID:");
        int customerId = Integer.parseInt(scanner.nextLine());

        try {
            Customer customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                System.out.println("Customer details:");
                System.out.println(customer);
            } else {
                System.out.println("Customer not found with ID: " + customerId);
            }
        } catch (AuthenticationException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getCustomerByUsername(Scanner scanner) {
        System.out.println("Enter Customer Username:");
        String username = scanner.nextLine();

        try {
            Customer customer = customerService.getCustomerByUsername(username);
            if (customer != null) {
                System.out.println("Customer details:");
                System.out.println(customer);
            } else {
                System.out.println("Customer not found with username: " + username);
            }
        } catch (AuthenticationException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteCustomer(Scanner scanner) {
        System.out.println("Enter Customer ID to delete:");
        int customerId = Integer.parseInt(scanner.nextLine());

        try {
            customerService.deleteCustomer(customerId);
            System.out.println("Customer deleted successfully!");
        } catch (AuthenticationException | DatabaseConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
