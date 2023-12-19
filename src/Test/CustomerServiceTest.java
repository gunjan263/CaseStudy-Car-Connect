package Test;

import Dao.CustomerService;

import Main.AuthenticationHandler;
import Main.MainModule;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService customerService = new CustomerService(); // Instantiate CustomerService

    @Test
    public void testCustomerAuthenticationWithInvalidCredentials() {
        assertFalse(AuthenticationHandler.authenticateCustomer("invalidUsername", "invalidPassword"));
    }

}

