package lk.ijse.dep8.controller;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep8.db.DBConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.util.HashMap;

public class FinalReportFormController {
    public TextField txtInput;
    public AnchorPane pneContainer;
    private Connection connection;
    private SwingNode swingNode;

    public void initialize() {
        connection = DBConnection.getInstance().getConnection();
        swingNode = new SwingNode();
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        pneContainer.getChildren().add(swingNode);
    }

    public void btnSearch_OnAction(ActionEvent actionEvent) {
        try {
//            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/final-report.jrxml"));
//            JasperDesign subJasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/sub-report.jrxml"));

            //ObjectInputStream ois = new ObjectInputStream(this.getClass().getResourceAsStream("/report/final-report.jasper"));
            //ObjectInputStream ois2 = new ObjectInputStream(this.getClass().getResourceAsStream("/report/sub-report.jasper"));
            JasperReport jasperReport = (JasperReport)
                    JRLoader.loadObject(this.getClass().getResource("/report/final-report.jasper")); //JasperCompileManager.compileReport(jasperDesign);
            JasperReport subJasperReport = (JasperReport)
                    JRLoader.loadObject(this.getClass().getResource("/report/sub-report.jasper")); //JasperCompileManager.compileReport(subJasperDesign);

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("query", txtInput.getText());
            parameters.put("subReport", subJasperReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            //JasperViewer.viewReport(jasperPrint, false);
            JRViewer viewer = new JRViewer(jasperPrint);
            viewer.setZoomRatio(0.75f);

            SwingUtilities.invokeLater(() -> {
                swingNode.setContent(viewer);
            });

            txtInput.requestFocus();
            txtInput.selectAll();
        } catch (JRException  e) {
            e.printStackTrace();
        }
    }
}
