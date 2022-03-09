package lk.ijse.dep8.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lk.ijse.dep8.util.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class ManageCustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSaveCustomer;
    public Button btnViewReport;
    public Button btnExport;
    public TableView<CustomerTM> tblCustomers;

    public void initialize() {

        /* Columns mapping */
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<CustomerTM, Button> lastCol =
                (TableColumn<CustomerTM, Button>) tblCustomers.getColumns().get(3);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction((event -> tblCustomers.getItems().remove(param.getValue())));
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
    }

    public void btnSaveCustomer_OnAction(ActionEvent event) {
        /* Let's do a little validation */

        if (!txtId.getText().matches("C\\d{3}")) {
            txtId.requestFocus();
            txtId.selectAll();
            return;
        } else if (txtName.getText().trim().isEmpty()) {
            txtName.requestFocus();
            txtName.selectAll();
            return;
        } else if (txtAddress.getText().trim().isEmpty()) {
            txtAddress.requestFocus();
            txtAddress.selectAll();
            return;
        }

        tblCustomers.getItems().add(new CustomerTM(txtId.getText(),
                txtName.getText(), txtAddress.getText()));

        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtId.requestFocus();
    }

    private JasperPrint getJasperPrint(){
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/customer-report1.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            HashMap<String, Object> parameters = new HashMap<>();

//            CustomerTM[] customers = new CustomerTM[tblCustomers.getItems().size()];
//            int i = 0;
//            for (CustomerTM customer : tblCustomers.getItems()) {
//                customers[i++] = customer;
//            }

            CustomerTM[] customers = tblCustomers.getItems().toArray(new CustomerTM[]{});

            return JasperFillManager.fillReport(jasperReport, parameters, new JRBeanArrayDataSource(customers));
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void btnExport_OnAction(ActionEvent event) {

    }

    public void btnViewReport_OnAction(ActionEvent event) {
        JasperPrint jasperPrint = getJasperPrint();
        JasperViewer.viewReport(jasperPrint, false);
    }

}
