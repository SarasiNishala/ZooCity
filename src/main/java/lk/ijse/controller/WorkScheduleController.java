package lk.ijse.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.Tm.WorkScheduleTm;
import lk.ijse.dto.WorkScheduleDto;
import lk.ijse.model.WorkScheduleModel;
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
import java.util.regex.Pattern;

public class WorkScheduleController {
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colHours;

    @FXML
    private TableColumn<?, ?> colScheduleId;

    @FXML
    private Label lblScheduleId;

    @FXML
    private Label lblDate;

    @FXML
    private TableView<WorkScheduleTm> tblWorkSchedule;

    @FXML
    private TextField txtHours;


    @FXML
    void btnClearOnAction(ActionEvent event) {clearFields();}

    @FXML
    void btnEditOnAction(ActionEvent event) {
        boolean b = validateSchedule();
        if (b){
            String id = lblScheduleId.getText();
            int hours = Integer.parseInt(txtHours.getText());
            LocalDate date = LocalDate.parse(lblDate.getText());

            var dto = new WorkScheduleDto(id,hours,date);

            var model = new WorkScheduleModel();
            try {
                boolean isSaved = model.editSchedule(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Schedule Updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean b = validateSchedule();

        if(b){
            String id = lblScheduleId.getText();
            int hours = Integer.parseInt(txtHours.getText());
            LocalDate date = LocalDate.parse(lblDate.getText());

            var dto = new WorkScheduleDto(id,hours,date);

            var model = new WorkScheduleModel();
            try {
                boolean isSaved = model.saveSchedule(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Schedule saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reprts/Schedule.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText("SELECT * FROM Schedule");
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

    void clearFields() {
        lblScheduleId.setText("");
        txtHours.clear();
        lblDate.setText("");
    }
    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblDate.setText(date);
    }

    public void initialize() {
        setDate();
        generateNextScheduleId();
        loadAllData();

        colScheduleId.setCellValueFactory(new PropertyValueFactory<>("ScheduleId"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextScheduleId() {
        try {
            String SchduleId = WorkScheduleModel.generateNextScheduleId();
            lblScheduleId.setText(SchduleId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateSchedule() {

        String hours = (txtHours.getText());
        boolean isValidTel = Pattern.matches("([1-9]{1})", hours);

        if (!isValidTel){
            new Alert(Alert.AlertType.ERROR, "Hour Invalid").show();
            return false;
        }

        return true;
    }

    private void loadAllData() {

        try {
            WorkScheduleModel workScheduleModel = new WorkScheduleModel();
            List<WorkScheduleDto> dtoList = workScheduleModel.getAll();

            ObservableList<WorkScheduleTm> obList = FXCollections.observableArrayList();

            for(WorkScheduleDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblWorkSchedule.getSelectionModel().getSelectedIndex();
                        String code = (String) colScheduleId.getCellData(selectedIndex);

                        deleteSchedule(code);

                        obList.remove(selectedIndex);
                        tblWorkSchedule.refresh();
                    }
                });
                var tm = new WorkScheduleTm(
                        dto.getScheduleId(),
                        dto.getHours(),
                        dto.getDate(),
                        btn
                );
                obList.add(tm);
            }
            tblWorkSchedule.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteSchedule(String code) {
        try {
            boolean isDeleted = WorkScheduleModel.deleteSchedule(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }
}
