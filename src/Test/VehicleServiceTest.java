package Test;

import Dao.VehicleService;
import Entity.Vehicle;
import Exception.DatabaseConnectionException;
import Exception.VehicleNotFoundException;
import Main.VehicleServiceMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleServiceTest {

    private VehicleService vehicleService;

    @BeforeEach
    public void setUp() {
        vehicleService = new VehicleService();
    }

    @Test
    public void testAddVehicle() {
        try {
            Vehicle newVehicle = new Vehicle(); // Create a new Vehicle object
            // Set necessary details for the new vehicle
            newVehicle.setModel("Model XYZ");
            newVehicle.setMake("Tesla");
            newVehicle.setYear(2023);
            newVehicle.setColor("Blue");
            newVehicle.setRegistrationNumber("ABC123");
            newVehicle.setAvailable(true);
            newVehicle.setDailyRate(50.0);

            vehicleService.addVehicle(newVehicle);
            // Assuming addVehicle doesn't throw an exception on success, no need for assertions
        } catch (DatabaseConnectionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateVehicle() {
        try {
            // Get an existing vehicle and update its details
            int existingVehicleId = 3; // Replace with an existing vehicle ID
            Vehicle existingVehicle = vehicleService.getVehicleById(existingVehicleId);

            // Update details of the existing vehicle
            existingVehicle.setModel("Hundai ");
            existingVehicle.setMake("Mahindra");
            // Set other updated details

            vehicleService.updateVehicle(existingVehicle);
            // Assuming updateVehicle doesn't throw an exception on success, no need for assertions
        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testGetAllVehicles() throws DatabaseConnectionException {

        VehicleService vehicleService = new VehicleService();

        List<Vehicle> allVehicles = VehicleServiceMenu.getAvailableVehiclesn();
    }
}