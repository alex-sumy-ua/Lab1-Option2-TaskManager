package com.plachkovskyy.aleksandr.tasks.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class MainController {

    final static Logger logger = Logger.getLogger(StartController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");

    private String mode;

    public String getMode() {return mode; }
    public void setMode(String mode) {
        this.mode = mode;
        modeLabel.setText("Using " + mode + " list");
    }

/*    public MainController() {
        setMode("current");
    }
*/

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button calendarButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button addButton;

    @FXML
    private Label modeLabel;

    @FXML
    void onCalendarButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/calendarWindow.fxml"));
            stage.setTitle("TaskManager: calendar");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            stage.setTitle("TaskManager: add new task");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDetailsButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            stage.setTitle("TaskManager: view/edit current task");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExitButtonPressed(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {


    }
}
