package lk.ijse.dep8.db;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection(){
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("/application.properties"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s:%s/%s", prop.getProperty("app.ip"),
                    prop.getProperty("app.port"), prop.getProperty("app.database"));

            connection = DriverManager.getConnection(url,
                    prop.getProperty("app.username"), prop.getProperty("app.password"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to establish the database connection", e);
        }
    }

    public static DBConnection getInstance(){
        return (dbConnection == null)? dbConnection = new DBConnection(): dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
