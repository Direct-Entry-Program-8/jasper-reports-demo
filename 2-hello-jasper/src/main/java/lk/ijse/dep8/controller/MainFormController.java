package lk.ijse.dep8.controller;

import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class MainFormController {

    public void btnShowHelloJasper_OnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.
                    load(this.getClass().getResourceAsStream("/report/hello-jasper.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            HashMap<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(5));

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
