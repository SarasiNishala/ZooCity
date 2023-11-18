package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.cert.PolicyNode;

public class LoginFormController {
    public TextField txtPassword;
    public TextField txtUsername;

    private AnchorPane root;
    public void btnOnActionLogin(ActionEvent actionEvent) throws IOException {


        String userName = txtUsername.getText();
        String pw = txtPassword.getText();

        System.out.println(userName);
        System.out.println(pw);

            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
            Scene scene = new Scene(rootNode);

            root.getChildren().clear();
            Stage primaryStage = (Stage) root.getScene().getWindow();

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("DashBoard");

    }
}
