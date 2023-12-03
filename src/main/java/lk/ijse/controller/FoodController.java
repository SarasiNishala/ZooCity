package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dto.AnimalDto;
import lk.ijse.dto.FoodDto;
import lk.ijse.dto.Tm.AnimalTm;
import lk.ijse.dto.Tm.FoodTm;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.FoodModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FoodController {
    @FXML
    public Label lblStatus;
    @FXML
    private Label lblFoodId;

    @FXML
    private TextField txtFoodName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;
    @FXML
    private TableView <FoodTm> tblFood;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colFoodId;

    @FXML
    private TableColumn<?, ?> colFoodName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;


    @FXML
    void btnClearOnAction(ActionEvent event) {clearFields();}

    @FXML
    void btnEditOnAction(ActionEvent event) {
        boolean b = validateFood();
        if (b){
            String id = lblFoodId.getText();
            String foodName = txtFoodName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());

            var dto = new FoodDto(id,foodName,price,qty);

            var model = new FoodModel();
            try {
                boolean isSaved = model.editFood(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Food Updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean b = validateFood();

        if(b){
            String id = lblFoodId.getText();
            String foodName = txtFoodName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());

            var dto = new FoodDto(id,foodName,price,qty);

            var model = new FoodModel();
            try {
                boolean isSaved = model.saveFood(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Food saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    private boolean validateFood() {
        String name = txtFoodName.getText();
        boolean isValid = Pattern.matches("([a-zA-Z\\s]+)", name);

        if (!isValid){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        String price = (txtPrice.getText());
        boolean isValidAddress = Pattern.matches("(\\d{2,})", price);

        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR, "Invalid price").show();
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
    void clearFields() {
        lblFoodId.setText("");
        txtFoodName.clear();
        txtPrice.clear();
        txtQty.clear();
    }
    private void generateNextFoodId() {
        try {
            String foodId = FoodModel.generateNextFoodId();
            lblFoodId.setText(foodId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        setCellValueFactory();
        loadAllFood();
        generateNextFoodId();
    }

    private void loadAllFood() {
        try {
            FoodModel foodModel = new FoodModel();
            List<FoodDto> dtoList = foodModel.getAll();

            ObservableList<FoodTm> obList = FXCollections.observableArrayList();

            for(FoodDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblFood.getSelectionModel().getSelectedIndex();
                        String code = (String) colFoodId.getCellData(selectedIndex);

                        deleteFood(code);

                        obList.remove(selectedIndex);
                        tblFood.refresh();
                    }
                });
                var tm = new FoodTm(
                        dto.getFoodId(),
                        dto.getName(),
                        dto.getPrice(),
                        dto.getQty(),
                        btn
                );
                obList.add(tm);
            }
            tblFood.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFood(String code) {
        try {
            boolean isDeleted = FoodModel.deleteFood(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "FOod deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("FoodId"));
        colFoodName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void btnAnimalsFood(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/AnimalFoods.fxml"));
        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.centerOnScreen();

        stage.show();
    }
}