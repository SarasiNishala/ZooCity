package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class DashBoardController {

    public Pane root;
    @FXML
    private ImageView aniRec;

    @FXML
    private ImageView cageRec;

    @FXML
    private ImageView empRec;

    @FXML
    private ImageView foodRec;

    @FXML
    private ImageView homeRec;

    @FXML
    private ImageView mediRec;

    @FXML
    private ImageView ticketRec;

    @FXML
    void animalOnClick(MouseEvent event) throws IOException {
        homeRec.setVisible(false);
        empRec.setVisible(false);
        aniRec.setVisible(true);
        cageRec.setVisible(false);
        mediRec.setVisible(false);
        foodRec.setVisible(false);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/AnimalsManage.fxml")));
    }

    @FXML
    void cageOnClick(MouseEvent event) throws IOException {
        homeRec.setVisible(false);
        empRec.setVisible(false);
        aniRec.setVisible(false);
        cageRec.setVisible(true);
        mediRec.setVisible(false);
        foodRec.setVisible(false);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/CageController.fxml")));
    }

    @FXML
    void employeeOnClick(MouseEvent event) throws IOException {
        homeRec.setVisible(false);
        empRec.setVisible(true);
        aniRec.setVisible(false);
        cageRec.setVisible(false);
        mediRec.setVisible(false);
        foodRec.setVisible(false);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/EmployeeManage.fxml")));
    }

    @FXML
    void foodOnClick(MouseEvent event) throws IOException {
        homeRec.setVisible(false);
        empRec.setVisible(false);
        aniRec.setVisible(false);
        cageRec.setVisible(false);
        mediRec.setVisible(false);
        foodRec.setVisible(true);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/FoodManage.fxml")));
    }

    @FXML
    void homeOnClick(MouseEvent event) throws IOException {
        homeRec.setVisible(true);
        empRec.setVisible(false);
        aniRec.setVisible(false);
        cageRec.setVisible(false);
        mediRec.setVisible(false);
        foodRec.setVisible(false);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/DashBoardPne.fxml")));
    }

    @FXML
    void logOutClick(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("DashBoard");
    }

    @FXML
    void medicineOnClic(MouseEvent event) throws IOException {
        homeRec.setVisible(false);
        empRec.setVisible(false);
        aniRec.setVisible(false);
        cageRec.setVisible(false);
        mediRec.setVisible(true);
        foodRec.setVisible(false);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/MedicineManage.fxml")));
    }

    @FXML
    void ticketOnClick(MouseEvent event) throws IOException {
        homeRec.setVisible(false);
        empRec.setVisible(false);
        aniRec.setVisible(false);
        cageRec.setVisible(false);
        mediRec.setVisible(false);
        foodRec.setVisible(false);
        ticketRec.setVisible(true);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/TicketManage.fxml")));
    }
    public void initialize() throws IOException {
        homeRec.setVisible(true);
        empRec.setVisible(false);
        aniRec.setVisible(false);
        cageRec.setVisible(false);
        mediRec.setVisible(false);
        foodRec.setVisible(false);
        ticketRec.setVisible(false);

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(root.getClass().getResource("/view/DashBoardPne.fxml")));
    }
}
