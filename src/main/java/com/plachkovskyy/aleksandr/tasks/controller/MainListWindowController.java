package com.plachkovskyy.aleksandr.tasks.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.plachkovskyy.aleksandr.tasks.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class MainListWindowController {

    final static Logger logger = Logger.getLogger(StartController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");

    private String mode;
    private Model model = new Model();

    public String getMode() {return mode; }
    public void setMode(String mode) {
        this.mode = mode;
        modeLabel.setText("Using " + mode + " list");
        //*********************************************
        if (mode.equals("new")) {
            model.changeFile("/resources/tasklist.txt");
        } else if (mode.equals("loaded")) {
            // do continue
        }
        // do continue



        //*********************************************
    }

//    public MainController(String mode) {
//        this.mode = mode;
//    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button calendarButton;

    @FXML
    private TableColumn<?, ?> atColumn;

    @FXML
    private TableColumn<?, ?> activeColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private Button detailsButton;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> fromColumn;

    @FXML
    private Button addButton;

    @FXML
    private Label modeLabel;

    @FXML
    private TableColumn<?, ?> toColumn;

    @FXML
    private TableColumn<?, ?> intervalColumn;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            Parent root = loader.load();
            // set deleteButton enabled **********************************************//
            AddEditTaskWindow controller = loader.<AddEditTaskWindow>getController(); //
            controller.setDeletable(false);                                           //
            // ***********************************************************************//
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/addEditTaskWindow.fxml"));
            Parent root = loader.load();
            // set deleteButton enabled **** transmitting parameters into controller *//
            AddEditTaskWindow controller = loader.<AddEditTaskWindow>getController(); //
            controller.setDeletable(true);                                            //
            // ***********************************************************************//
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
        logger.info("Logger: application finished at MainWindow.exitButton.");
        System.exit(0);
    }

    @FXML
    void initialize() {
        logger.info("Logger: MainWindow initialized.");


    }
}
