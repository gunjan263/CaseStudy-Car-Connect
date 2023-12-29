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
            newVehicle.setModel("Model XYZ");
            newVehicle.setMake("Tesla");
            newVehicle.setYear(2023);
            newVehicle.setColor("Blue");
            newVehicle.setRegistrationNumber("ABC123");
            newVehicle.setAvailable(true);
            newVehicle.setDailyRate(50.0);

            vehicleService.addVehicle(newVehicle);

        } catch (DatabaseConnectionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateVehicle() {
        try {

            int existingVehicleId = 3;
            Vehicle existingVehicle = vehicleService.getVehicleById(existingVehicleId);

            existingVehicle.setModel("Hundai ");
            existingVehicle.setMake("Mahindra");

            vehicleService.updateVehicle(existingVehicle);

        } catch (VehicleNotFoundException | DatabaseConnectionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testGetAllVehicles() {

        VehicleService vehicleService = new VehicleService();

        List<Vehicle> allVehicles = VehicleServiceMenu.getAvailableVehiclesn();
    }
}