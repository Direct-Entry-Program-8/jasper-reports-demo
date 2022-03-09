package lk.ijse.dep8.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.naming.ldap.HasControls;
import java.util.HashMap;
import java.util.Map;

public class CustomerFormController {
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtID;

    public void btnShowReport_OnAction(ActionEvent actionEvent) {

        /* Quick Validation */
        if (!txtID.getText().matches("C\\d{3}")){
            txtID.requestFocus();
            return;
        }else if (txtName.getText().trim().isEmpty()){
            txtName.requestFocus();
            return;
        }else if (txtAddress.getText().trim().isEmpty()){
            txtAddress.requestFocus();
            return;
        }

        /* Let's show the report */
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/report-with-params.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", txtID.getText());
            parameters.put("name", txtName.getText());
            parameters.put("address", txtAddress.getText());
            JasperPrint jasperPrint = JasperFillManager.
                    fillReport(jasperReport, parameters, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
