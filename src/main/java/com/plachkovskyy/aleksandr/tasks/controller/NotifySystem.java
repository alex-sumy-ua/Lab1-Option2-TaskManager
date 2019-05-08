package com.plachkovskyy.aleksandr.tasks.controller;

import com.plachkovskyy.aleksandr.tasks.model.Model;
import com.plachkovskyy.aleksandr.tasks.model.Task;
import org.apache.log4j.Logger;
//import eu.hansolo.enzo.notification.Notification;
//import javafx.event.ActionEvent;
//import javafx.scene.control.Alert;
import java.awt.*;
import java.util.Date;

/**
 * The class for sending notifications.
 */
public class NotifySystem extends Thread {

    private Model model;
    private TrayIcon trayIcon;
    final static Logger logger = Logger.getLogger(StartDialogController.class);

    /**
     * Constructor.
     * @param model
     * @param trayIcon
     */
    public NotifySystem(Model model, TrayIcon trayIcon) {
        this.model = model;
        this.trayIcon = trayIcon;
    }

    @Override
    public void run() {
        while (true) {
            for (Task task : model.getTasks()){
                Date nextTime = task.nextTimeAfter(new Date());
                if(nextTime == null) {
                    continue;
                }
                if (nextTime.getTime() < (new Date(System.currentTimeMillis() + 60_000).getTime())) {
                    trayIcon.displayMessage(task.getTitle(),
                                      "Let's complete this task at\n\"" + task.nextTimeAfter(new Date()) + "\"",
                                            TrayIcon.MessageType.INFO);
//                    Notification info = new Notification(task.getTitle(), "Let's complete the task at\n\"" +
//                                                         task.nextTimeAfter(new Date()) + "\"");
//                    Notification.Notifier.INSTANCE.notify(info);
                    logger.info("notification about the task \"" + task.getTitle() + "\" at \"" +
                                 task.nextTimeAfter(new Date()) + "\" was sent.");
                }
            }
            try {
                sleep(60_000);
            } catch (InterruptedException e) {
                logger.error("it was impossible to sleep because of: " + e.getMessage());
            }
        }
    }

// *** Notification sending examples ***********************************************************************************
// void onNotifyButtonPressed(ActionEvent actionEvent) {
    // Create a custom Notification without icon
/*        Notification info = new Notification("Title", "Info-Message");
        // Show the custom notification
        Notification.Notifier.INSTANCE.notify(info);
*/
    // Show a predefined notification
/*        Notification.Notifier.INSTANCE.notifyInfo("Title3", "Message3");
        logger.info("notification message was sent.");
        //.notifySuccess("Title1","Message1");
        //.notifyError("Title2", "Message2");
        // .notifyInfo("Title3", "Message3");
        // .notifyWarning("Title4", "Message4");
    }
*/
//**********************************************************************************************************************
/*                    Notification info = new Notification("Warning",
                                                     "Let's complete the task \"" + task.getTitle() +
                                                               "\" at \"" + task.nextTimeAfter(new Date()) + "\".");
                    Notification.Notifier.INSTANCE.notify(info);
*/
//**********************************************************************************************************************
/*    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText("Look, an Information Dialog");
    alert.setContentText("I have a great message for you!");
    alert.showAndWait();
*/
}
