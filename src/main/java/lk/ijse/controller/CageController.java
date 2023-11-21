package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.CageDto;
import lk.ijse.dto.Tm.CageTm;
import lk.ijse.model.CageModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class CageController {
    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAnimalNo;

    @FXML
    private TableColumn<?, ?> colCageId;

    @FXML
    private TableColumn<?, ?> colCageType;

    @FXML
    private Label lblCageId;

    @FXML
    private TableView<CageTm> tblCage;

    @FXML
    private TextField txtNo;

    @FXML
    void btnCageControl(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {clearFields();}

    @FXML
    void btnEditOnAction(ActionEvent event) {
        boolean b = validateCage();
        if (b){
            String id = lblCageId.getText();
            int no = Integer.parseInt(txtNo.getText());
            String type = cmbType.getValue();

            var dto = new CageDto(id,type,no);

            var model = new CageModel();
            try {
                boolean isSaved = model.editCage(dto);
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
        boolean b = validateCage();

        if(b){
            String id = lblCageId.getText();
            int no = Integer.parseInt(txtNo.getText());
            String type = cmbType.getValue();

            var dto = new CageDto(id,type,no);

            var model = new CageModel();
            try {
                boolean isSaved = model.saveCage(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Cage saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    void clearFields() {
        lblCageId.setText("");
        txtNo.clear();
        cmbType.setValue("");
    }

    private boolean validateCage() {

        String no = (txtNo.getText());
        boolean isValidTel = Pattern.matches("(\\d{1,})", no);

        if (!isValidTel){
            new Alert(Alert.AlertType.ERROR, "Invalid number").show();
            return false;
        }

        return true;
    }

    public  void setComboBox() {
        cmbType.getItems().add("Mammals");
        cmbType.getItems().add("Birds");
        cmbType.getItems().add("Fish");
    }

    public void initialize() {
        setComboBox();
        setListener();
        loadAllData();

        colCageId.setCellValueFactory(new PropertyValueFactory<>("CageId"));
        colCageType.setCellValueFactory(new PropertyValueFactory<>("CageTYpe"));
        colAnimalNo.setCellValueFactory(new PropertyValueFactory<>("NoOfAnimals"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setListener() {
        tblCage.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new CageDto(
                            newValue.getCageId(),
                            newValue.getType(),
                            newValue.getNoOfANimals()
                    );
                    setFields(dto);
                });
    }

    private void setFields(CageDto dto) {
        lblCageId.setText(dto.getCageId());
        cmbType.setValue(dto.getType());
        txtNo.setText(String.valueOf(dto.getNoOfANimals()));
    }

    private void loadAllData() {

        try {
            CageModel cageModel = new CageModel();
            List<CageDto> dtoList = cageModel.getAll();

            ObservableList<CageTm> obList = FXCollections.observableArrayList();

            for(CageDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblCage.getSelectionModel().getSelectedIndex();
                        String code = (String) colCageId.getCellData(selectedIndex);

                        deleteCage(code);

                        obList.remove(selectedIndex);
                        tblCage.refresh();
                    }
                });
                var tm = new CageTm(
                        dto.getCageId(),
                        dto.getType(),
                        dto.getNoOfANimals(),
                        btn
                );
                obList.add(tm);
            }
            tblCage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteCage(String code) {
        try {
            boolean isDeleted = CageModel.deleteCage(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "cage deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }
}
