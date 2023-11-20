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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.Tm.EmployeeTm;
import lk.ijse.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public class AddEmployeeController{

    public AnchorPane root;
    public TableColumn <?,?> colEmpAddress;
    public TableView<EmployeeTm> tblEmployee;
    public TableColumn <?,?> colEmpId;
    public TableColumn  <?,?> colEmpName;
    public TableColumn  <?,?> colEmpTel;
    public TableColumn  <?,?> colCategory;
    public TableColumn  <?,?> colShiftId;
    public TableColumn  <?,?> colAdminId;
    public TableColumn <?,?> colAction;
    @FXML
    public ComboBox  <String> cmbShiftId;
    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpContact;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;




    public void btnSaveOnAction(ActionEvent actionEvent) {

        boolean b = validateCustomer();

        if(b){
            String id = txtEmpId.getText();
            String name = txtEmpName.getText();
            String address = txtEmpAddress.getText();
            String category = String.valueOf(cmbCategory.getValue());
            int tel = Integer.parseInt(txtEmpContact.getText());
            String shiftId = cmbShiftId.getValue();

            var dto = new EmployeeDto(id,name,address,tel,category, shiftId,"A001");

            var model = new EmployeeModel();
            try {
                boolean isSaved = model.saveEmployee(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            AddEmployeeController emp = new AddEmployeeController();
        }
    }

    public void btnEditOnAction(ActionEvent actionEvent) {

        boolean b = validateCustomer();
        if (b){
            String id = txtEmpId.getText();
            String name = txtEmpName.getText();
            String category = String.valueOf(cmbCategory.getValue());
            String address = txtEmpAddress.getText();
            int tel = Integer.parseInt(txtEmpContact.getText());
            String shiftId = cmbShiftId.getValue();



            var dto = new EmployeeDto(id,name,address,tel,category, shiftId ,"A001");

            var model = new EmployeeModel();
            try {
                boolean isSaved = model.editEmployee(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtEmpId.getText();
        EmployeeModel model = new EmployeeModel();

        EmployeeDto resultSet = model.searchEmployee(id);

        if(resultSet==null){
            System.out.println("SOMETHING WENT WRONG...");
        }else {
            txtEmpId.setText(resultSet.getEmpId());
            txtEmpName.setText(resultSet.getEmpName());
            txtEmpAddress.setText(resultSet.getEmpAddress());
            txtEmpContact.setText(String.valueOf(resultSet.getEmpContact()));
            cmbCategory.setValue(resultSet.getCategory());
            cmbShiftId.setValue(resultSet.getShiftId());
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {clearFields();}

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/Salary.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnWorkScheduleOnAction(ActionEvent actionEvent) {
    }
    void clearFields() {
        txtEmpId.clear();
        txtEmpName.clear();
        cmbCategory.setValue("");
        txtEmpAddress.clear();
        txtEmpContact.clear();
        cmbShiftId.setValue("");
    }
    public  void setComboBox(){
        cmbCategory.getItems().add("Doctor");
        cmbCategory.getItems().add("Cleaner");
        cmbCategory.getItems().add("Supplier");

        ObservableList<String> obList = FXCollections.observableArrayList();
            try {
                List<EmployeeDto> list = EmployeeModel.getAll();

                for (EmployeeDto dto : list) {
                    obList.add(dto.getShiftId());
                }

                cmbShiftId.setItems(obList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public void initialize(){
        setComboBox();
        setCellValueFactory();
        loadAllData();
        setListener();
    }

    private void setListener() {
        tblEmployee.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new EmployeeDto(
                            newValue.getEmpId(),
                            newValue.getEmpName(),
                            newValue.getEmpAddress(),
                            newValue.getEmpContact(),
                            newValue.getCategory(),
                            newValue.getShiftId(),
                            newValue.getAdminId()
                    );
                    setFields(dto);
                });
    }

    private void setFields(EmployeeDto dto) {
        txtEmpId.setText(dto.getEmpId());
        txtEmpName.setText(dto.getEmpName());
        txtEmpAddress.setText(dto.getEmpAddress());
        txtEmpContact.setText(String.valueOf(dto.getEmpContact()));
        cmbCategory.setValue(dto.getCategory());
        cmbCategory.setValue(dto.getShiftId());
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colEmpTel.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colShiftId.setCellValueFactory(new PropertyValueFactory<>("shiftId"));
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllData() {

        try {
            EmployeeModel employeeModel = new EmployeeModel();
            List<EmployeeDto> dtoList = employeeModel.getAll();

            ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

            for(EmployeeDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblEmployee.getSelectionModel().getSelectedIndex();
                        String code = (String) colEmpId.getCellData(selectedIndex);

                        deleteEmp(code);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblEmployee.refresh();
                    }
                });
                var tm = new EmployeeTm(
                        dto.getEmpId(),
                        dto.getEmpName(),
                        dto.getEmpAddress(),
                        dto.getEmpContact(),
                        dto.getCategory(),
                        dto.getShiftId(),
                        dto.getAdminId(),
                        btn
                );
                obList.add(tm);
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteEmp(String code) {
        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private boolean validateCustomer() {
        String id = txtEmpId.getText();
        boolean isValid = Pattern.matches("([DCS][0-9]{3,})", id);

        if (!isValid){
            new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            return false;
        }

        String name = txtEmpName.getText();
        boolean isValidName = Pattern.matches("([a-zA-Z\\s]+)", name);

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        String address = txtEmpAddress.getText();
        boolean isValidAddress = Pattern.matches("([a-zA-Z0-9\\s]+)", address);

        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR, "Invalid Address").show();
            return false;
        }

        String tel = txtEmpContact.getText();
        boolean isValidTel = Pattern.matches("[0-9]{10}", tel);

        if (!isValidTel){
            new Alert(Alert.AlertType.ERROR, "Invalid Tel").show();
            return false;
        }

        return true;
    }
}
