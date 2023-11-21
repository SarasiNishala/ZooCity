package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lk.ijse.dto.AnimalsFoodDto;
import lk.ijse.dto.AnimalsMediDto;
import lk.ijse.model.AnimalsFoodModel;
import lk.ijse.model.AnimalsMediModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class AnimalMedicineController {
    @FXML
    private ComboBox<String> cmbAnimalId;

    @FXML
    private ComboBox<String> cmbMediId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnClearOnAction(ActionEvent event) {clearFields();}

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = cmbAnimalId.getValue();

        var animalsMediModel = new AnimalsMediModel();
        try {
            boolean isDeleted = animalsMediModel.deleteANimalMedi(id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, " deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        boolean b = validateAnimaMedi();
        if (b){
            String mediId = cmbMediId.getValue();
            String animalId = cmbAnimalId.getValue();
            int qty = Integer.parseInt(txtQty.getText());
            String status = txtStatus.getText();
            LocalDate date = LocalDate.parse(lblDate.getText());
            LocalDateTime time = LocalDateTime.parse(lblTime.getText());

            var dto = new AnimalsMediDto(animalId,mediId,date,time,qty,status);

            var model = new AnimalsMediModel();
            try {
                boolean isSaved = model.editANimalsMedi(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Animals Food Updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean b = validateAnimaMedi();

        if(b){
            String mediId = cmbMediId.getValue();
            String animalId = cmbAnimalId.getValue();
            int qty = Integer.parseInt(txtQty.getText());
            String status = txtStatus.getText();
            LocalDate date = LocalDate.parse(lblDate.getText());
            LocalDateTime time = LocalDateTime.parse(lblTime.getText());

            var dto = new AnimalsMediDto(animalId,mediId,date,time,qty,status);

            var model = new AnimalsMediModel();
            try {
                boolean isSaved = model.saveAnimalsMedi(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Animal Food Saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    private void generateRealTime() {
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void initialize() {
        generateRealTime();

    }
    void clearFields() {
        cmbAnimalId.setValue("");
        cmbMediId.setValue("");
        txtQty.clear();
        txtStatus.clear();
    }
    private boolean validateAnimaMedi() {
        String status = txtStatus.getText();
        boolean isValid = Pattern.matches("([a-zA-Z\\s]+)", status);

        if (!isValid){
            new Alert(Alert.AlertType.ERROR, "Invalid status").show();
            return false;
        }
        String qty = (txtQty.getText());
        boolean isValidTel = Pattern.matches("(\\d{1,})", qty);

        if (!isValidTel){
            new Alert(Alert.AlertType.ERROR, "Invalid Qty").show();
            return false;
        }

        return true;
    }

    public  void setComboBox(){

        ObservableList<String> obListAni = FXCollections.observableArrayList();
        try {
            List<AnimalsMediDto> list = AnimalsMediModel.getAllAnimal();

            for (AnimalsMediDto dto : list) {
                obListAni.add(dto.getAnimalTg());
            }

            cmbAnimalId.setItems(obListAni);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> obListFd = FXCollections.observableArrayList();
        try {
            List<AnimalsMediDto> list = AnimalsMediModel.getAll();

            for (AnimalsMediDto dto : list) {
                obListFd.add(dto.getMediId());
            }

            cmbMediId.setItems(obListFd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
