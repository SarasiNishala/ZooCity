package lk.ijse.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.MedicineDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.TicketDto;
import lk.ijse.dto.Tm.MedicineTm;
import lk.ijse.dto.Tm.SalaryTm;
import lk.ijse.model.CageModel;
import lk.ijse.model.EmployeeModel;
import lk.ijse.model.MedicineModel;
import lk.ijse.model.SalaryModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class SalaryController {
    @FXML
    public TableColumn <?,?>colAction;
    @FXML
    public TableColumn colDate;
    @FXML
    private TableColumn<?, ?> ColPayment;

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblPayment;

    @FXML
    private Label lblSalaryId;

    @FXML
    private TableView <SalaryTm> tblSalary;

    private void clearFields() {
        lblSalaryId.setText("");
        cmbEmpId.setValue("");
        lblPayment.setText("");
        lblDate.setText("");
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblDate.setText(date);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String SalaryId = lblSalaryId.getText();
        String EmpId = cmbEmpId.getValue();
        Double payment = Double.valueOf(lblPayment.getText());
        LocalDate date = LocalDate.parse(lblDate.getText());

        var dto = new SalaryDto(SalaryId,EmpId,payment,date);

        var model = new SalaryModel();
        try {
            boolean isSaved = model.saveSalary(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {clearFields();}

    public void btnReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reprts/SalaryEmp.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText("SELECT * FROM Salary");
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());

            JFrame frame = new JFrame("Jasper Report Viewer");
            JRViewer viewer = new JRViewer(jasperPrint);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer);
            frame.setSize(new Dimension(1200, 800));
            frame.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        setDate();
        setComboBox();
        loadAllSalary();
        generateNextSalaryId();
        setListener();
        setCellValueFactory();
    }

    private void setComboBox() {
        ObservableList<String> obListEmpId = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> list = EmployeeModel.getAllEmp();

            for (EmployeeDto dto : list) {
                obListEmpId.add(dto.getEmpId());
            }

            cmbEmpId.setItems(obListEmpId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSalary() {
        try {
            SalaryModel salaryModel = new SalaryModel();
            List<SalaryDto> dtoList = salaryModel.getAllSalary();

            ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

            for(SalaryDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblSalary.getSelectionModel().getSelectedIndex();
                        String code = (String) colSalaryId.getCellData(selectedIndex);

                        deleteSalary(code);

                        obList.remove(selectedIndex);
                        tblSalary.refresh();
                    }
                });
                var tm = new SalaryTm(
                        dto.getSalaryId(),
                        dto.getEmpId(),
                        dto.getPayment(),
                        dto.getDate(),
                        btn
                );
                obList.add(tm);
            }
            tblSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setListener() {
        tblSalary.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new SalaryDto(
                            newValue.getSalaryId(),
                            newValue.getEmpId(),
                            newValue.getPayment(),
                            newValue.getDate()
                    );
                    setFields(dto);
                });
    }

    private void setFields(SalaryDto dto) {
        lblSalaryId.setText(dto.getSalaryId());
        cmbEmpId.setValue(dto.getEmpId());
        lblSalaryId.setText(String.valueOf(dto.getPayment()));
        lblDate.setText(String.valueOf(dto.getDate()));
    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("SalaryId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
        ColPayment.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    private void deleteSalary(String code) {
        try {
            boolean isDeleted = SalaryModel.deleteSalary(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "Salary deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void generateNextSalaryId() {
        try {
            String salaryId = SalaryModel.generateNextSalaryId();
            lblSalaryId.setText(salaryId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectIdOnAction(ActionEvent actionEvent) {
        ObservableList<String> obListEmpId = FXCollections.observableArrayList();
        try {
            String empId = cmbEmpId.getValue();
            String empCategory = EmployeeModel.checkCategory(empId);

            if (empCategory.equals("Doctor")) {
                lblPayment.setText("60000.00");
            } else if (empCategory.equals("Cleaner")) {
                lblPayment.setText("30000.00");
            } else if (empCategory.equals("Supplier")) {
                lblPayment.setText("25000.00");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
