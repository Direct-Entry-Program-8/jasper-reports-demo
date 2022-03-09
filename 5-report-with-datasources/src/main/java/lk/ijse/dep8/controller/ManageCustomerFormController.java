package lk.ijse.dep8.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import lk.ijse.dep8.util.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.util.HashMap;

public class ManageCustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSaveCustomer;
    public Button btnViewReport;
    public Button btnExport;
    public TableView<CustomerTM> tblCustomers;

    private boolean asAnArray;

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

    public void initData(boolean asAnArray){
        this.asAnArray = asAnArray;
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

    private JasperPrint getJasperPrint() {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/customer-report1.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            HashMap<String, Object> parameters = new HashMap<>();

//            CustomerTM[] customers = new CustomerTM[tblCustomers.getItems().size()];
//            int i = 0;
//            for (CustomerTM customer : tblCustomers.getItems()) {
//                customers[i++] = customer;
//            }

            if (asAnArray) {
                CustomerTM[] customers = tblCustomers.getItems().toArray(new CustomerTM[]{});

                return JasperFillManager.fillReport(jasperReport, parameters, new JRBeanArrayDataSource(customers));
            }else{
                return JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(tblCustomers.getItems()));
            }
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void btnExport_OnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Customer Report");
        fileChooser.setInitialFileName("customer-report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
        File file = fileChooser.showSaveDialog(btnExport.getScene().getWindow());

        if (file != null) {
            String path = file.getAbsolutePath();

            if (!System.getProperty("os.name").equalsIgnoreCase("windows")) {
                path += ".pdf";
            }

            JasperPrint jasperPrint = getJasperPrint();
            try {
                JasperExportManager.exportReportToPdfFile(jasperPrint, path);
                new Alert(Alert.AlertType.INFORMATION, "Exported successfully").show();
            } catch (JRException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to export, try again").show();
            }
        }

    }

    public void btnViewReport_OnAction(ActionEvent event) {
        JasperPrint jasperPrint = getJasperPrint();
        JasperViewer.viewReport(jasperPrint, false);
    }

}
