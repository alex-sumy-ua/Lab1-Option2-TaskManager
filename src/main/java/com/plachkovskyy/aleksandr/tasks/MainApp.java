package com.plachkovskyy.aleksandr.tasks;

import com.plachkovskyy.aleksandr.tasks.controller.StartDialogController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.log4j.Logger;


public class MainApp extends Application {

    final static Logger logger = Logger.getLogger(MainApp.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("application started.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/startDialog.fxml"));
        Parent root = loader.load();
        StartDialogController startDialogController = loader.getController();
        primaryStage.setTitle("Select start mode");
        primaryStage.setScene(new Scene(root, 270, 100));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(startDialogController.getCloseEventHandler());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);// -> autocalling start(Stage primaryStage)
    }

}
