package Test;

import Dao.VehicleService;
import Entity.Vehicle;
import Exception.DatabaseConnectionException;
import Exception.VehicleNotFoundException;
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
            newVehicle.setModel("Camaro");
            newVehicle.setMake("Chevrolet");
            newVehicle.setYear(2024);
            newVehicle.setColor("White");
            newVehicle.setRegistrationNumber("EX345");
            newVehicle.setAvailable(true);
            newVehicle.setDailyRate(25000);

            vehicleService.addVehicle(newVehicle);

        } catch (DatabaseConnectionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateVehicle() {
        try {

            int existingVehicleId = 4;
            Vehicle existingVehicle = vehicleService.getVehicleById(existingVehicleId);

            existingVehicle.setModel("Model Y");
            existingVehicle.setMake("Tesla");
            existingVehicle.setYear(2023);
            existingVehicle.setColor("Matte Black");
            existingVehicle.setRegistrationNumber("ABC123");
            existingVehicle.setAvailable(false);
            existingVehicle.setDailyRate(25000);



            vehicleService.updateVehicle(existingVehicle);

        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testToGetAllVehicle() {
        try {
            // Retrieve all vehicles
            List<Vehicle> vehicles = vehicleService.getAllVehicles();

            // Assertions:
            assertNotNull(vehicles);
            assertFalse(vehicles.isEmpty());
            assertTrue(!vehicles.isEmpty());
            Vehicle firstVehicle = vehicles.get(0);
            assertNotNull(firstVehicle.getMake());
            assertNotNull(firstVehicle.getModel());
            assertNotNull(firstVehicle.getYear());
            assertNotNull(firstVehicle.getColor());
            assertNotNull(firstVehicle.getRegistrationNumber());
            assertNotNull(firstVehicle.isAvailable());
            assertNotNull(firstVehicle.getDailyRate());

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage()); // Fail the test if an exception occurs
        }
    }

    @Test
    public void testToGetAvailableVehicle() {
        try {
            // Retrieve all vehicles
            List<Vehicle> vehicles = vehicleService.getAvailableVehicles();

            // Assertions:
            assertNotNull(vehicles);
            assertFalse(vehicles.isEmpty());
            assertTrue(!vehicles.isEmpty());
            Vehicle firstVehicle = vehicles.get(0);
            assertNotNull(firstVehicle.getMake());
            assertNotNull(firstVehicle.getModel());
            assertNotNull(firstVehicle.getYear());
            assertNotNull(firstVehicle.getColor());
            assertNotNull(firstVehicle.getRegistrationNumber());
            assertNotNull(firstVehicle.isAvailable());
            assertNotNull(firstVehicle.getDailyRate());

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage()); // Fail the test if an exception occurs
        }
    }
}