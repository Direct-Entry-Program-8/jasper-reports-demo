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

    public void btnDB_OnAction(ActionEvent actionEvent) {
    }

    private void loadManageCustomerForm(boolean asAnArray) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/ManageCustomerForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Customer Form: " + ((asAnArray) ? "Bean Array DS": "Bean Collection DS"));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.root.getScene().getWindow());
        stage.show();
        stage.centerOnScreen();
    }
}
