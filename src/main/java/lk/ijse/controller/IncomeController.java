package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class IncomeController {
    @FXML
    private TableView<?> tblIncome;
    @FXML
    private TextField lblSearch;
    @FXML
    private TextField lblDailyIncome;
    
    @FXML
    private TableColumn<?, ?> ColAmount;

    @FXML
    private TableColumn<?, ?> ColDate;

    @FXML
    private TableColumn<?, ?> clAction;

    @FXML
    private TableColumn<?, ?> colIncomeId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblIncomeId;



}
