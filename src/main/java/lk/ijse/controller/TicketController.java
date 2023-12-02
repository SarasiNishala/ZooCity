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
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.TicketDto;
import lk.ijse.dto.Tm.EmployeeTm;
import lk.ijse.dto.Tm.TicketTm;
import lk.ijse.model.CageModel;
import lk.ijse.model.EmployeeModel;
import lk.ijse.model.SalaryModel;
import lk.ijse.model.TicketModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TicketController {
    @FXML
    private TableColumn<?, ?> ColIncomeId;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableColumn<?, ?> colAdminId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colTicketNo;

    @FXML
    private TableColumn<?, ?> colTicketType;

    @FXML
    private Label lblIncomeId;
    @FXML
    private Label lblDate;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTicketId;

    @FXML
    private TableView<TicketTm> tblTicket;
    @FXML
    void btnEditOnAction(ActionEvent event) {
        String TicketNo = lblTicketId.getText();
        String TicketType = cmbType.getValue();
        Double price = Double.valueOf(lblPrice.getText());
        String IncomeId = lblIncomeId.getText();
        String adminId = lblIncomeId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());

       var dto = new TicketDto(TicketNo,TicketType,price,IncomeId,adminId,date);

       var model = new TicketModel();
        try {
            boolean isUpdated = model.editTicket(dto);
           System.out.println(isUpdated);
           if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, " updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String TicketNo = lblTicketId.getText();
        String TicketType = cmbType.getValue();
        Double price = Double.valueOf(lblPrice.getText());
        String IncomeId = lblIncomeId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());

        var dto = new TicketDto(TicketNo,TicketType,price,IncomeId,"A001",date);

        var model = new TicketModel();
        try {
            boolean isSaved = model.saveTicket(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearFields() {
        lblTicketId.setText("");
        cmbType.setValue("");
        lblPrice.setText("");
        lblIncomeId.setText("");
        lblDate.setText("");
    }

    public void initialize(){
        setComboBox();
        setCellValueFactory();
        loadAllData();
        setListener();
        generateNextTicketId();
        generateNextIncomeId();
        setDate();
    }
    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblDate.setText(date);
    }

    private void setListener() {
        tblTicket.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new TicketDto(
                            newValue.getTicketNo(),
                            newValue.getTicketType(),
                            newValue.getPrice(),
                            newValue.getIncomeId(),
                            newValue.getAdminId(),
                            newValue.getDate()
                    );
                    setFields(dto);
                });
    }

    private void setFields(TicketDto dto) {
        lblIncomeId.setText(dto.getTicketNo());
        cmbType.setValue(dto.getTicketType());
        lblPrice.setText(String.valueOf(dto.getPrice()));
        lblDate.setText(String.valueOf(dto.getDate()));
        lblIncomeId.setText(dto.getIncomeId());
    }

    private void loadAllData() {
        var model = new TicketModel();

        ObservableList<TicketTm> obList = FXCollections.observableArrayList();

        try {
            List<TicketDto> dtoList = model.getAllTicket();

            for(TicketDto dto : dtoList) {
                obList.add(
                        new TicketTm(
                                dto.getTicketNo(),
                                dto.getTicketType(),
                                dto.getPrice(),
                                dto.getDate(),
                                dto.getAdminId(),
                                dto.getIncomeId()
                        )
                );
            }

            tblTicket.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setComboBox() {
        cmbType.getItems().add("Full");
        cmbType.getItems().add("Half");
    }

    private void setCellValueFactory() {
        colTicketNo.setCellValueFactory(new PropertyValueFactory<>("TicketNo"));
        colTicketType.setCellValueFactory(new PropertyValueFactory<>("TicketType"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ColIncomeId.setCellValueFactory(new PropertyValueFactory<>("IncomeId"));
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("AdminId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    private void generateNextTicketId() {
        try {
            String ticketId = TicketModel.generateNextTicketId();
            lblTicketId.setText(ticketId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextIncomeId() {
        try {
            String incomeId = TicketModel.generateNextIncomeId();
            lblIncomeId.setText(incomeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectTypeOnAction(ActionEvent event) throws SQLException {
        String TicketType = cmbType.getValue();

        if (TicketType.equals("Full")) {
            lblPrice.setText("300.00");
        } else if (TicketType.equals("Half")) {
            lblPrice.setText("200.00");
        }
    }
}
