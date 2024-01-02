package Dao;

import Entity.Vehicle;
import Exception.VehicleNotFoundException;
import Exception.DatabaseConnectionException;
import java.util.List;

public interface IVehicleService {
    Vehicle getVehicleById(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException;
    List<Vehicle> getAvailableVehicles() throws DatabaseConnectionException;
    List<Vehicle> getAllVehicles() throws DatabaseConnectionException;
    void addVehicle(Vehicle vehicleData) throws DatabaseConnectionException;
    void updateVehicle(Vehicle vehicleData) throws VehicleNotFoundException, DatabaseConnectionException;
    void removeVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException;

}
