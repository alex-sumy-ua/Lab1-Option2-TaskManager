package com.plachkovskyy.aleksandr.tasks.controller;


import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.apache.log4j.Logger;

public class CalendarController {

    final static Logger logger = Logger.getLogger(CalendarController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button applyButton;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private Button closeButton;

    @FXML
    void onApplyButtonPressed(ActionEvent event) {

    }

    @FXML
    void onCloseButtonPressed(ActionEvent event) {
        closeButton.getScene().getWindow().hide();
    }

    @FXML
    void onDatePicker1Choose(ActionEvent event) {

    }

    @FXML
    void onDatePicker2Choose(ActionEvent event) {

    }

    @FXML
    void initialize() {
        datePicker1.setValue(LocalDate.now());
        datePicker2.setValue(LocalDate.now().plusDays(7));

    }
}
