package Util;

import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString() {
        // Constructing connection string based on provided credentials
        String database = "CarRentalDatabase";
        String username = "root";
        String password = "Gunjan@2001";

        return "jdbc:mysql://localhost:3306/" + database + "?user=" + username + "&password=" + password;
    }
}
