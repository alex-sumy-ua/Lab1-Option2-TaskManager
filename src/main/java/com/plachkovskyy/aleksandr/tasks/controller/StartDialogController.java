package com.plachkovskyy.aleksandr.tasks.controller;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

/**
 * The class that offers to choose tasks file: current, loaded or new.
 */
public class StartDialogController {

    final static Logger logger = Logger.getLogger(StartDialogController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");
    private String mode;
    private String filename;

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

    /**
     * Getter for mode.
      * @return String mode.
     */
    public String getMode() {
        return mode;
    }

    /**
     * Setter of String mode.
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Getter for filename.
     * @return String filename.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Setter of String filename.
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Constructor.
     */
    public StartDialogController() {
        setMode("current");
        setFilename("tasklist.txt");
    }

    /**
     * Event handler onOKButtonPressed.
     * @param actionEvent
     */
    public void onOKButtonPressed(ActionEvent actionEvent) {
        setMode(comboBox.getValue());
        //********* FileChooser ****************************************************************************************
        if (getMode().equals("loaded")) {                                                                            //*
            FileChooser fileChooser = new FileChooser();                                                             //*
            fileChooser.setTitle("Open file for loading");                                                           //*
            fileChooser.getExtensionFilters().addAll(                                                                //*
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),                    //*
//                    /*new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),                   //*
//                    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),*/                   //*
                    new FileChooser.ExtensionFilter("All Files", "*.*"));                      //*
            File selectedFile = fileChooser.showOpenDialog(okButton.getScene().getWindow());                         //*
            setFilename(selectedFile.getAbsolutePath());                                                             //*
        } else if (getMode().equals("new")) {                                                                        //*
            setFilename("tasklist.txt");                                                                             //*
        }                                                                                                            //*
        logger.info("StartDialog was closed by OKButton.");                                                          //*
        //********* FileChooser ****************************************************************************************
        okButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/fxml/mainListWindow.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        }
        // parameter transferring into MainController ******************************************************************
        MainListWindowController mainListWindowController = loader.getController();                                  //*
        mainListWindowController.setMode(getMode());                                                                 //*
        mainListWindowController.setFilename(getFilename());                                                         //*
        mainListWindowController.initFile();                                                                         //*
        logger.info("using " + getMode() + " list.");                                                                //*
        mainListWindowController.showMainTable();                                                                    //*
        // *************************************************************************************************************
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("TaskManager: task list");
        stage.setMinHeight(500);
        stage.setMinWidth(875);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(mainListWindowController.getCloseEventHandler());
        stage.showAndWait();
    }

    /**
     * Event handler onCancelButtonPressed.
     * @param actionEvent
     */
    public void onCancelButtonPressed(ActionEvent actionEvent) {
        logger.info("StartDialog was closed.");
        logger.info("application terminated.");
        System.exit(0);
    }

    /**
     * Event handler onComboBoxChecked.
     * @param actionEvent
     */
    public void onComboBoxChecked(ActionEvent actionEvent) {
            setMode(comboBox.getValue());
    }

    // onCloseDialog ***************************************************************************************************
    /**                                                                                                              //*
     * Event handler onCloseWindow                                                                                   //*
     */                                                                                                              //*
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {//*
        @Override                                                                                                    //*
        public void handle(WindowEvent event) {                                                                      //*
            onCancelButtonPressed(new ActionEvent());                                                                //*
        }                                                                                                            //*
    };                                                                                                               //*
                                                                                                                     //*
    /**                                                                                                              //*
     * Getter for event handler.                                                                                     //*
     * @return EventHandler<WindowEvent>.                                                                            //*
     */                                                                                                              //*
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){                                            //*
        return closeEventHandler;                                                                                    //*
    }                                                                                                                //*
    //******************************************************************************************************************

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

        logger.info("StartDialog initialized.");
        logger.info("choice option list dialog started.");

    }

}
