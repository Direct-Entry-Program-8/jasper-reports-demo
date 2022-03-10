package lk.ijse.dep8.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep8.db.DBConnection;
import lk.ijse.dep8.util.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CustomerFormController {
    public TableView<CustomerTM> tblCustomers;
    private Connection connection;

    public void initialize() throws ClassNotFoundException {

        connection = DBConnection.getInstance().getConnection();

        /* Mapping for columns */
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            loadCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(()->new Alert(Alert.AlertType.ERROR, "Failed to load customers").show());
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

    private void loadCustomers() throws SQLException{
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM customer");

        while (rst.next()) {
            tblCustomers.getItems().add(new CustomerTM(rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("address")));
        }
    }

    public void btnShowCustomerReport_OnAction(ActionEvent event) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/customer-report2.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            HashMap<String, Object> params = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Jasper Report").show();
        }
    }

    public void btnClearData_OnAction(ActionEvent event) {
        tblCustomers.getItems().clear();
    }

    public void btnLoadData_OnAction(ActionEvent event) throws SQLException {
        tblCustomers.getItems().clear();
        loadCustomers();
    }
}
