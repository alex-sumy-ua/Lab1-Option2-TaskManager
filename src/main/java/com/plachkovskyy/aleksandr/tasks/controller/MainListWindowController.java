package com.plachkovskyy.aleksandr.tasks.controller;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import com.plachkovskyy.aleksandr.tasks.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

/**
 * The class that shows the whole task list.
 */
public class MainListWindowController {

    final static Logger logger = Logger.getLogger(StartDialogController.class);
    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private Model model;
    private Task task;
    private String filename;
    private String mode;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label modeLabel;

    @FXML
    private Label loadedFileNameLabel;

    @FXML
    private Button calendarButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button addButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, Boolean> repeatedColumn;

    @FXML
    private TableColumn<Task, Date> atColumn;

    @FXML
    private TableColumn<Task, Date> fromColumn;

    @FXML
    private TableColumn<Task, Date> toColumn;

    @FXML
    private TableColumn<Task, Integer> intervalColumn;

    @FXML
    private TableColumn<Task, Boolean> activeColumn;

    /**
     * Constructor.
     */
    public MainListWindowController() {
        this.model = new Model();
        this.task = new Task();

    }

    /**
     * Getter for filename.
     * @return
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
        loadedFileNameLabel.setText(filename);
    }

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
        modeLabel.setText("Using " + mode + " list:");
    }

    /**
     * Setter of File for model.
     */
    public void initFile() {
        if (getMode().equals("current")) {
            model.readFile();
        } else if (getMode().equals("loaded")) {
            model.changeFile(getFilename());
        } else {  // "new"
            model.createFile(getFilename());
        }
    }

    /**
     * Return currently selected in the TableView task.
     * @return
     */
    public Task selectedTask() {
        Task selected = (Task) tableView.getSelectionModel().getSelectedItem();
        return selected;
    }

    /**
     * Return true in case of any row selected.
     * @return boolean isSelected any row.
     */
    public boolean isSelected() {
        return (tableView.getSelectionModel().getSelectedIndex() >= 0);
    }

    @FXML
    /**
     * Event handler onCalendarButtonPressed. CalendarWindow start.
      */
    void onCalendarButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/calendarWindow.fxml"));//******
            Parent root = loader.load();                                                                             //*
            //Parent root = FXMLLoader.load(getClass().getResource("/controller/fxml/calendarWindow.fxml"));-alternative
            // set deleteButton enabled ********************************************************************************
            CalendarWindowController calendarWindowController = loader.<CalendarWindowController>getController();                           //*
            calendarWindowController.setModel(this.model);                                                           //*
            calendarWindowController.showTableAll();                                                                 //*
            // *********************************************************************************************************
            stage.setTitle("TaskManager: calendar");
            stage.setMinHeight(400);
            stage.setMinWidth(700);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(calendarWindowController.getCloseEventHandler());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Event handler onAddButtonPressed. AddTaskWindow start.
      */
    void onAddButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/controller/fxml/addEditTaskWindow.fxml"));
            Parent root = loader.load();
            // set deleteButton enabled ********************************************************************************
            AddEditTaskWindow addEditTaskController = loader.<AddEditTaskWindow>getController();                     //*
            addEditTaskController.setDeletable(false);                                                               //*
            addEditTaskController.setModel(this.model);                                                              //*
            addEditTaskController.setAdd_NotEdit(true);         // adding a new task                                 //*
            addEditTaskController.setActiveCheckBox(true);      // activeCheckBox setSelected                        //*
            addEditTaskController.setMainListWindowController(this);                                                 //*
            // *********************************************************************************************************
            logger.info("adding new task by AddButton.");
            stage.setTitle("TaskManager: add new task");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(addEditTaskController.getCloseEventHandler());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Event handler onDetailsButtonPressed. Details/deleteWindow start.
     */
    void onDetailsButtonPressed(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/controller/fxml/addEditTaskWindow.fxml"));
            Parent root = loader.load();
            // set deleteButton enabled **** transmitting parameters into addEditTaskController ************************
            AddEditTaskWindow addEditTaskController = loader.<AddEditTaskWindow>getController();                                     //*
            if (!isSelected()) {                                                                                     //*
                logger.info("any line in table is not selected. Stop AddEditTaskWindow.");                           //*
                return;                                                                                              //*
            }                                                                                                        //*
            addEditTaskController.setDeletable(true);                                                                //*
            addEditTaskController.setTask(selectedTask());                                                           //*
            addEditTaskController.setModel(this.model);                                                              //*
            addEditTaskController.setAdd_NotEdit(false); // detalization / editing of chosen task                    //*
            addEditTaskController.setMainListWindowController(this);                                                 //*
            // *********************************************************************************************************
            logger.info("viewing/editing of chosen task by DetailsButton.");
            stage.setTitle("TaskManager: controller/edit current task");
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(addEditTaskController.getCloseEventHandler());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Event handler onExitButtonPressed. Application terminate.
      */
    void onExitButtonPressed(ActionEvent event) {
        this.setFilename("tasklist.txt");
        model.saveFile(getFilename());
        logger.info("MainListWindow was closed.");
        logger.info("application terminated.");
        System.exit(0);
    }

    /**
     * Prepare data for TableView.
     */
    public void initData() {
        tableView.impl_updatePeer();
        taskList.clear();
        for (int i = 0; i < model.getTasks().size(); i++) {
            taskList.add(i, model.getTask(i));
        }
    }

    /**
     * Binding data with TableView.
     */
    void showMainTable() {
        // preparation data for tableView
        // this could be database
        initData();
        // set type and value, have to be contained in the column
        titleColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        atColumn.setCellValueFactory(new PropertyValueFactory<Task, Date>("time"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<Task, Date>("start"));
        toColumn.setCellValueFactory(new PropertyValueFactory<Task, Date>("end"));
        intervalColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("interval"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("active"));
        repeatedColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("repeated"));
        // fill table with data
        tableView.setItems(taskList);
        tableView.refresh();
    }

    // onCloseWindow ***************************************************************************************************
    /**
     * Event handler onCloseWindow
     */
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {//*
        @Override                                                                                                    //*
        public void handle(WindowEvent event) {                                                                      //*
            onExitButtonPressed(new ActionEvent());                                                                  //*
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

    /**
     * Start notify System.
     */
    private void notifySystemStart(){

        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        NotifySystem notifySystem = new NotifySystem(this.model, trayIcon);
        notifySystem.setDaemon(true);
        notifySystem.start();
        logger.info("NotifySystem started.");

    }

    @FXML
    void initialize() {

        logger.info("MainListWindow initialized.");

        notifySystemStart();

    }

}
