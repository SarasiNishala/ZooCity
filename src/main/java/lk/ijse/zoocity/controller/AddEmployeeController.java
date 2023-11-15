package lk.ijse.zoocity.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.zoocity.dto.EmployeeDto;
import lk.ijse.zoocity.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;




public class AddEmployeeController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtAdmin;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpContact;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private PasswordField txtEmpPassword;

    @FXML
    private TextField txtShiftId;


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String password = txtEmpPassword.getText();
        String category = txtCategory.getText();
        String address = txtEmpAddress.getText();
        String tel = txtEmpContact.getText();
        String adminId = txtAdmin.getText();
        String shiftId = txtShiftId.getText();

        var dto = new EmployeeDto(id,name,password,category,address,tel,adminId,shiftId);

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

    public void btnEditOnAction(ActionEvent actionEvent) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String password = txtEmpPassword.getText();
        String category = txtCategory.getText();
        String address = txtEmpAddress.getText();
        String tel = txtEmpContact.getText();
        String adminId = txtAdmin.getText();
        String shiftId = txtShiftId.getText();

        var dto = new EmployeeDto(id,name,password,category,address,tel,adminId,shiftId);

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
        AddEmployeeController emp = new AddEmployeeController();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtEmpId.getText();

        var EmployeeModel = new EmployeeModel();
        try {
            boolean isDeleted = EmployeeModel.deleteCustomer(id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {clearFields();}

    public void btnSalaryOnAction(ActionEvent actionEvent) {
    }

    public void btnWorkScheduleOnAction(ActionEvent actionEvent) {
    }
    void clearFields() {
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtEmpPassword.setText("");
        txtCategory.setText("");
        txtEmpAddress.setText("");
        txtEmpContact.setText("");
        txtAdmin.setText("");
        txtShiftId.setText("");
    }
}
