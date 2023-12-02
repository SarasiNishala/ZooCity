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
import lk.ijse.dto.AnimalDto;
import lk.ijse.dto.AnimalsFoodDto;
import lk.ijse.dto.FoodDto;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.AnimalsFoodModel;
import lk.ijse.model.FoodModel;
import lk.ijse.model.MedicineModel;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AnimalFoodsController {
    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private ComboBox <String> cmbAnimalId;

    @FXML
    private ComboBox <String> cmbFoodId;

    @FXML
    private TextField txtQty;

    @FXML
    void btnClearOnAction(ActionEvent event) {clearFields();}

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = cmbAnimalId.getValue();

        var animalsFoodModel = new AnimalsFoodModel();
        try {
            boolean isDeleted = animalsFoodModel.deleteANimalFood(id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    public  void setComboBox(){

        ObservableList<String> obListAni = FXCollections.observableArrayList();
        try {
            List<AnimalDto> list = AnimalModel.getAll();

            for (AnimalDto dto : list) {
                obListAni.add(dto.getAnimalTg());
            }

            cmbAnimalId.setItems(obListAni);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> obListFd = FXCollections.observableArrayList();
        try {
            List<FoodDto> list = FoodModel.getAll();

            for (FoodDto dto : list) {
                obListFd.add(dto.getFoodId());
            }

            cmbFoodId.setItems(obListFd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        boolean b = validateAnimalFood();
        if (b){
            String foodId = cmbFoodId.getValue();
            String animalId = cmbAnimalId.getValue();
            int qty = Integer.parseInt(txtQty.getText());
            LocalDate date = LocalDate.parse(lblDate.getText());
            LocalDateTime time = LocalDateTime.parse(lblTime.getText());

            var dto = new AnimalsFoodDto(animalId,foodId,date,time,qty);

            var model = new AnimalsFoodModel();
            try {
                boolean isSaved = model.editANimalsFood(dto);
                if (isSaved) {
                    FoodModel foodModel = new FoodModel();
                    boolean isUpdateFood = foodModel.updateStock(foodId,qty);
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
        boolean b = validateAnimalFood();

        if(b){
            String foodId = cmbFoodId.getValue();
            String animalId = cmbAnimalId.getValue();
            int qty = Integer.parseInt(txtQty.getText());
            LocalDate date = LocalDate.parse(lblDate.getText());
            LocalDateTime time = LocalDateTime.parse(lblTime.getText());

            var dto = new AnimalsFoodDto(animalId,foodId,date,time,qty);

            var model = new AnimalsFoodModel();
            try {
                boolean isSaved = model.saveAnimalFood(dto);
                if (isSaved) {
                    FoodModel foodModel = new FoodModel();
                    boolean isUpdateFood = foodModel.updateStock(foodId,qty);
                    if (isUpdateFood){
                    new Alert(Alert.AlertType.CONFIRMATION, "Animal Food Saved!").show();
                    clearFields();}
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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
    void clearFields() {
        cmbAnimalId.setValue("");
        cmbFoodId.setValue("");
        txtQty.clear();
    }
    private boolean validateAnimalFood() {
        String qty = (txtQty.getText());
        boolean isValidTel = Pattern.matches("(\\d{1,})", qty);

        if (!isValidTel){
            new Alert(Alert.AlertType.ERROR, "Invalid Qty").show();
            return false;
        }
        return true;
    }
}
