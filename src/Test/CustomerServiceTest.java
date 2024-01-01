package Test;

import Dao.CustomerService;

import Entity.Customer;
import Main.AuthenticationHandler;
import Exception.AuthenticationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Exception.DatabaseConnectionException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;


public class CustomerServiceTest {

    @Test
    public void testCustomerAuthenticationWithInvalidCredentials() {
        assertFalse(AuthenticationHandler.authenticateCustomer("invalidUsername", "invalidPassword"));
    }

    @Test
    public void testUpdateCustomer() {

        try {
            CustomerService customerService = new CustomerService();
            int customerId = 3;
            Customer testCustomer = customerService.getCustomerById(customerId);
            testCustomer.setCustomerID(customerId);
            testCustomer.setFirstName("Arun");
            testCustomer.setLastName("Nagar");
            testCustomer.setEmail("arun@example");
            testCustomer.setPhoneNumber("+91-8957345693");
            testCustomer.setAddress("1204, Lane Colony Delhi");
            testCustomer.setUsername("arsharma");
            testCustomer.setPassword("arun1234");
            LocalDate localDate = LocalDate.parse("2023-12-12");
            java.util.Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            testCustomer.setRegistrationDate(sqlDate);

            customerService.updateCustomer(testCustomer);


        } catch (DatabaseConnectionException | AuthenticationException e) {
            e.printStackTrace();
        }
    }
}