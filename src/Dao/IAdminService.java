package Dao;
import Entity.Admin;
import Exception.AdminNotFoundException;
import Exception.DatabaseConnectionException;
public interface IAdminService {
    Admin getAdminById(int adminId) throws AdminNotFoundException, DatabaseConnectionException;
    Admin getAdminByUsername(String username) throws AdminNotFoundException, DatabaseConnectionException;
    void registerAdmin(Admin adminData) throws DatabaseConnectionException;
    void updateAdmin(Admin adminData) throws AdminNotFoundException, DatabaseConnectionException;
    void deleteAdmin(int adminId) throws AdminNotFoundException, DatabaseConnectionException;
}
