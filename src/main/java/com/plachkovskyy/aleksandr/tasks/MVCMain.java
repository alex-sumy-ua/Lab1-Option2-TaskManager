package com.plachkovskyy.aleksandr.tasks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class MVCMain extends Application {
    final static Logger logger = Logger.getLogger(MVCMain.class);

        @Override
        public void start(Stage primaryStage) throws Exception{
            logger.info("Logger: application started.");
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/startDialog.fxml"));
            primaryStage.setTitle("Select start mode");
            primaryStage.setScene(new Scene(root, 270, 100));
            primaryStage.setResizable(false);
            primaryStage.show();

        }

        public static void main(String[] args) {
            launch(args);
        }

//        logger.info("Logger testing - info");
//        logger.error("Logger testing - error");

}
