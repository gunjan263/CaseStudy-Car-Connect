package Dao;

import Entity.Customer;
import Exception.AuthenticationException;
import Exception.DatabaseConnectionException;

public interface ICustomerService {
    Customer getCustomerById(int customerId) throws AuthenticationException, DatabaseConnectionException;

    Customer getCustomerByUsername(String username) throws AuthenticationException, DatabaseConnectionException;

    void registerCustomer(Customer customerData) throws DatabaseConnectionException;

    void updateCustomer(Customer customerData) throws AuthenticationException, DatabaseConnectionException;

    void deleteCustomer(int customerId) throws AuthenticationException, DatabaseConnectionException;
}