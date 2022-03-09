package lk.ijse.dep8.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep8.util.CustomerTM;

import java.sql.*;

public class CustomerFormController {
    public TableView<CustomerTM> tblCustomers;
    private Connection connection;

    public void initialize() throws ClassNotFoundException {

        /* Mapping for columns */
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep8_test", "root", "mysql");
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customer");

            while (rst.next()) {
                tblCustomers.getItems().add(new CustomerTM(rst.getString("id"),
                        rst.getString("name"),
                        rst.getString("address")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to establish the database connection").show();
        }

        Platform.runLater(() -> {
            tblCustomers.getScene().getWindow().setOnCloseRequest(event -> {
                try {
                    if (!connection.isClosed()) {
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
