package lk.ijse.zoocity.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection con;
    private static DbConnection dbConnection;

    public DbConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZooCity","root","Ijse@1234");
    }
    public static DbConnection getInstance() throws SQLException {
        if (dbConnection == null){
            return dbConnection = new DbConnection();
        }
        else
            return dbConnection;
    }
    public Connection getConnection() {
        return con;
    }
}
