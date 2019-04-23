package com.plachkovskyy.aleksandr.tasks;

import com.plachkovskyy.aleksandr.tasks.view.View;
import com.plachkovskyy.aleksandr.tasks.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.log4j.Logger;


public class Controller extends Application {
    final static Logger logger = Logger.getLogger(Controller.class);
    private Model model;
    private View view;

/*    public Controller(Model model, View view) throws Exception {
        this.model = model;
        this.view = view;
        logger.info("Logger: controller initialized successfully.");
        this.start(new Stage());
    }
*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Logger: application started.");
        Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/startDialog.fxml"));
        primaryStage.setTitle("Select start mode");
        primaryStage.setScene(new Scene(root, 270, 100));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);// -> autocalling start(Stage primaryStage)
    }

}
