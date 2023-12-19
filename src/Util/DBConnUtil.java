package Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection() throws SQLException {
        String connectionString = DBPropertyUtil.getConnectionString();
        return DriverManager.getConnection(connectionString);
    }
}


