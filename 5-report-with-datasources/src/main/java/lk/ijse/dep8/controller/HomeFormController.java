package lk.ijse.dep8.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeFormController {
    public AnchorPane root;

    public void btnArray_OnAction(ActionEvent actionEvent) throws IOException {
        loadManageCustomerForm(true);
    }

    public void btnCollection_OnAction(ActionEvent actionEvent) throws IOException {
        loadManageCustomerForm(false);
    }

    public void btnDB_OnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/CustomerForm.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Report: Database DS");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.root.getScene().getWindow());
        stage.show();
        stage.centerOnScreen();
    }

    private void loadManageCustomerForm(boolean asAnArray) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ManageCustomerForm.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        ManageCustomerFormController ctrl = fxmlLoader.getController();
        ctrl.initData(asAnArray);

        stage.setTitle("Manage Customer Form: " + ((asAnArray) ? "Bean Array DS" : "Bean Collection DS"));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.root.getScene().getWindow());
        stage.show();
        stage.centerOnScreen();
    }

    public void btnFinalReport_OnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/FinalReportForm.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Final Report for the Day");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.root.getScene().getWindow());
        stage.show();
        stage.centerOnScreen();
    }
}
