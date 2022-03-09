package lk.ijse.dep8.controller;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageFormController {
    public void btnLegacy_OnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.
                    load(this.getClass().getResourceAsStream("/report/birthday-wish.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("image2", this.getClass().getResource("/report/assets/dulanga.jpg"));
            parameters.put("image1", this.getClass().getResource("/report/assets/happy-birth-day.jpg"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnNew_OnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.
                    load(this.getClass().getResourceAsStream("/report/birthday-wish2.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.
                    fillReport(jasperReport, parameters, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.
                    load(this.getClass().getResourceAsStream("/report/birthday-wish.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("image2", this.getClass().getResource("/report/assets/dulanga.jpg"));
            parameters.put("image1", this.getClass().getResource("/report/assets/happy-birth-day.jpg"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(1));

            JasperPrintManager.printPage(jasperPrint, 0, true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnExport_OnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.
                    load(this.getClass().getResourceAsStream("/report/birthday-wish2.jrxml"));

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.
                    fillReport(jasperReport, parameters, new JREmptyDataSource(1));

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML File","*.html"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File","*.pdf"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File","*.xml"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null){
                JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getAbsolutePath() + ".html");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}
