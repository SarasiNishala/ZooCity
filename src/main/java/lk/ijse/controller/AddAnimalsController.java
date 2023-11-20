package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.AnimalDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.Tm.AnimalTm;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddAnimalsController {

    public TableColumn <?,?>colAdminId;
    public TableColumn <?,?> colAction;
    public ComboBox <String> cmbCageId;
    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TableColumn<?, ?> colAniType;

    @FXML
    private TableColumn<?, ?> colAnimalTg;

    @FXML
    private TableColumn<?, ?> colCageId;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<AnimalTm> tblAnimal;

    @FXML
    private TextField txtAniTg;

    @FXML
    private TextField txtType;

    @FXML
    void btnClearOnAction(ActionEvent event) {clearFields();}

    @FXML
    void btnEditOnAction(ActionEvent event) {
        boolean b = validateAnimal();
        if (b){
            String tag = txtAniTg.getText();
            String category = String.valueOf(cmbCategory.getValue());
            String type = txtType.getText();
            String cageId = cmbCageId.getValue();

            var dto = new AnimalDto(tag,category,type,cageId,"A001");

            var model = new AnimalModel();
            try {
                boolean isSaved = model.editAnimal(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Animal Updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean b = validateAnimal();

        if(b){
            String tag = txtAniTg.getText();
            String category = String.valueOf(cmbCategory.getValue());
            String type = txtType.getText();
            String cageId = cmbCageId.getValue();

            var dto = new AnimalDto(tag,category,type,cageId,"A001");

            var model = new AnimalModel();
            try {
                boolean isSaved = model.saveAnimal(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Animal saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {
        String tag = txtAniTg.getText();
        AnimalModel model = new AnimalModel();

        AnimalDto resultSet = model.searchAnimal(tag);

        if(resultSet==null){
            System.out.println("SOMETHING WENT WRONG...");
        }else {
            txtAniTg.setText(resultSet.getAnimalTg());
            cmbCategory.setValue(resultSet.getCategory());
            txtType.setText(resultSet.getAnimalType());
            cmbCageId.setValue(resultSet.getCageId());
        }
    }
    private boolean validateAnimal() {
        String id = txtAniTg.getText();
        boolean isValid = Pattern.matches("[A-Z][0-9]{3,}", id);

        if (!isValid){
            new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            return false;
        }

        String type = txtType.getText();
        boolean isValidAddress = Pattern.matches("([a-zA-Z\\s]+)", type);

        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR, "Invalid Type").show();
            return false;
        }
        return true;
    }
    void clearFields() {
        txtAniTg.clear();
        cmbCategory.setValue("");
        txtType.clear();
        cmbCageId.setValue("");
    }

    public  void setComboBox(){
        cmbCategory.getItems().add("Mamals");
        cmbCategory.getItems().add("Birds");
        cmbCategory.getItems().add("Fish");

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<AnimalDto> list = AnimalModel.getAll();

            for (AnimalDto dto : list) {
                obList.add(dto.getCageId());
            }

            cmbCageId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(){
        setComboBox();
        loadAllData();
        setListener();

        colAnimalTg.setCellValueFactory(new PropertyValueFactory<>("animalTg"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAniType.setCellValueFactory(new PropertyValueFactory<>("animalType"));
        colCageId.setCellValueFactory(new PropertyValueFactory<>("cageId"));
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("AdminId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setListener() {
        tblAnimal.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new AnimalDto(
                            newValue.getAnimalTg(),
                            newValue.getCategory(),
                            newValue.getAnimalType(),
                            newValue.getCageId(),
                            newValue.getAdminId()
                    );
                    setFields(dto);
                });
    }

    private void setFields(AnimalDto dto) {
        txtAniTg.setText(dto.getAnimalTg());
        cmbCategory.setValue(dto.getCategory());
        cmbCageId.setValue(dto.getCageId());
    }

    private void loadAllData() {

        try {
            AnimalModel animalModel = new AnimalModel();
            List<AnimalDto> dtoList = animalModel.getAll();

            ObservableList<AnimalTm> obList = FXCollections.observableArrayList();

            for(AnimalDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblAnimal.getSelectionModel().getSelectedIndex();
                        String code = (String) colAnimalTg.getCellData(selectedIndex);

                        deleteAnimal(code);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblAnimal.refresh();
                    }
                });
                var tm = new AnimalTm(
                        dto.getAnimalTg(),
                        dto.getCategory(),
                        dto.getAnimalType(),
                        dto.getCageId(),
                        dto.getAdminId(),
                        btn
                );
                obList.add(tm);
            }
            tblAnimal.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteAnimal(String code) {
        try {
            boolean isDeleted = AnimalModel.deleteAnimal(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }
}
