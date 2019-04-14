package com.plachkovskyy.aleksandr.tasks.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import org.apache.log4j.Logger;

public class AddEditTaskWindow {

    final static Logger logger = Logger.getLogger(CalendarController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker atDatePicker;

    @FXML
    private RadioButton nonRepeatedRadioButton;

    @FXML
    private RadioButton repeatedRadioButton;

    @FXML
    private Button addButton;

    @FXML
    void onAddButtonPressed(ActionEvent event) {
        //
        addButton.getScene().getWindow().hide();

    }

    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    void onNonRepeatedRadioButton(ActionEvent event) {

    }

    @FXML
    void onRepeatedRadioButton(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
