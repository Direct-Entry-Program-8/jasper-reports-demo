package lk.ijse.dep8.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep8.util.CustomerTM;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class CustomerFormController {
    public TableView<CustomerTM> tblCustomers;
    private Connection connection;

    public void initialize() throws ClassNotFoundException {

        Properties prop = new Properties();
        try {
            prop.load(Files.newInputStream(Paths.get("/home/ranjith-suranga/application.properties")));
            //prop.load(this.getClass().getResourceAsStream("/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(()->{
                new Alert(Alert.AlertType.ERROR, "Failed to load configurations").show();
            });
            return;
        }

        /* Mapping for columns */
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            String url = String.format("jdbc:mysql://%s:%s/%s", prop.getProperty("app.ip"),
                    prop.getProperty("app.port"), prop.getProperty("app.database"));
            this.connection = DriverManager.getConnection(url,
                    prop.getProperty("app.username"), prop.getProperty("app.password"));
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customer");

            while (rst.next()) {
                tblCustomers.getItems().add(new CustomerTM(rst.getString("id"),
                        rst.getString("name"),
                        rst.getString("address")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(()->{
                new Alert(Alert.AlertType.ERROR, "Failed to establish the database connection").show();
            });
            return;
        }

        Platform.runLater(() -> {
            tblCustomers.getScene().getWindow().setOnCloseRequest(event -> {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void btnShowCustomerReport_OnAction(ActionEvent event) {

    }
}
