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
import lk.ijse.dto.CageManageFormDto;
import lk.ijse.model.CageManageFormModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class CageManageFormController {
    @FXML
    private ComboBox<String> cmbCageId;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = cmbEmployeeId.getValue();

        var cageControllForm = new CageManageFormModel();
        try {
            boolean isDeleted = cageControllForm.deleteCageForm(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnEditOnAction(ActionEvent event) {

        String empId = cmbEmployeeId.getValue();
        String cageId = cmbCageId.getValue();
        LocalDate date = LocalDate.parse(lblDate.getText());
        LocalDateTime time = LocalDateTime.parse(lblTime.getText());

        var dto = new CageManageFormDto(empId, cageId, date, time);

        var model = new CageManageFormModel();
        try {
            boolean isSaved = model.editCageControlForm(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, " Updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String empId = cmbEmployeeId.getValue();
        String cageId = cmbCageId.getValue();
        LocalDate date = LocalDate.parse(lblDate.getText());
        LocalDateTime time = LocalDateTime.parse(lblTime.getText());

        var dto = new CageManageFormDto(empId, cageId, date, time);

        var model = new CageManageFormModel();
        try {
            boolean isSaved = model.saveCageForm(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    void clearFields() {
        cmbEmployeeId.setValue("");
        cmbCageId.setValue("");
    }

    public void setComboBox() {

        ObservableList<String> obListEmp = FXCollections.observableArrayList();
        try {
            List<CageManageFormDto> list = CageManageFormModel.getAllEmployee();

            for (CageManageFormDto dto : list) {
                obListEmp.add(dto.getEmpId());
            }

            cmbEmployeeId.setItems(obListEmp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> obListCage = FXCollections.observableArrayList();
        try {
            List<CageManageFormDto> list = CageManageFormModel.getAllCages();

            for (CageManageFormDto dto : list) {
                obListCage.add(dto.getCageId());
            }

            cmbCageId.setItems(obListCage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        generateRealTime();
        setComboBox();

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
}


