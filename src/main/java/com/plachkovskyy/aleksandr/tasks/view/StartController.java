package com.plachkovskyy.aleksandr.tasks.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class StartController {

    final static Logger logger = Logger.getLogger(StartController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");

    private String mode;
    public String getMode() {return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public StartController() {
        setMode("current");
    }

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button cancelButton;

        @FXML
        private ComboBox<String> comboBox;

        @FXML
        private Button okButton;

    public void onOKButtonPressed(ActionEvent actionEvent) {
        setMode(comboBox.getValue());
        okButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/fxml/mainListWindow.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Logger: " + e.getStackTrace());
        }
        // parameter transferring into MainController *********************************
        MainListWindowController mainListWindowController = loader.getController(); //*
        mainListWindowController.setMode(getMode());                                //*
        logger.info("Logger: Using " + getMode() + " list.");                       //*
//        MainController mainController = new MainController(getMode());            //*
//        loader.setController(mainController);                                     //*
        // ****************************************************************************
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("TaskManager: task list");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void onCancelButtonPressed(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onComboBoxChecked(ActionEvent actionEvent) {
            setMode(comboBox.getValue());
    }

    @FXML
    void initialize() {

        comboBox.getItems().addAll("current", "loaded", "new");
        comboBox.setValue(getMode());
        okButton.setOnAction(event -> {
            onOKButtonPressed(new ActionEvent());
        });

        cancelButton.setOnAction(event -> {
            onCancelButtonPressed(new ActionEvent());
        });

        comboBox.setOnAction(event -> {
            onComboBoxChecked(new ActionEvent());
        });

        logger.info("Logger: choice list option window started.");

    }

}
