package com.plachkovskyy.aleksandr.tasks.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import com.plachkovskyy.aleksandr.tasks.model.Model;
import com.plachkovskyy.aleksandr.tasks.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

/**
 * The class that builds timetable of tasks.
 */
public class CalendarWindowController {

    final static Logger logger = Logger.getLogger(CalendarWindowController.class);
    private ObservableList<Calendar> calendarList = FXCollections.observableArrayList();
    private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private Button applyButton;

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Calendar> tableView;

    @FXML
    private TableColumn<Calendar, Date> timeColumn;

    @FXML
    private TableColumn<Calendar, String> titleColumn;

    @FXML
    private TableColumn<Task, Boolean> repeatedColumn;

    @FXML
    void onDatePicker1Choose(ActionEvent event) {}

    /**
     * Getter for model.
     * @return Model model.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Setter of Model model.
     * @param model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Constructor.
     */
    public CalendarWindowController() {
        this.model  = new Model();
    }

    @FXML
    /**
     * Event handler onApplyButtonPressed.
     */
    void onApplyButtonPressed(ActionEvent event) {
        // checking DatePickers correct values
        if (datePicker2.getValue().isBefore(datePicker1.getValue())) {
            datePicker2.setValue(datePicker1.getValue());
            logger.info("\"to\" date could not be before \"from\" and was set as \"from\".");
        }
        // preparation data for tableView
        // this could be database
        initData();
        logger.info("new period was set by ApplyButton.");
    }

    @FXML
    /**
     * Event handler onCloseButtonPressed.
     */
    void onCloseButtonPressed(ActionEvent event) {
        closeButton.getScene().getWindow().hide();
        logger.info("CalendarWindow was closed.");
    }

    // onCloseWindow ***************************************************************************************************
    /**                                                                                                              //*
     * Event handler onCloseWindow.                                                                                  //*
     */                                                                                                              //*
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {//*
        @Override                                                                                                    //*
        public void handle(WindowEvent event) {                                                                      //*
            onCloseButtonPressed(new ActionEvent());                                                                 //*
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
     * Inner class for transmit data into TabeView.
     */
    public class Calendar {
        Date    date;
        String  taskTitle;
        boolean repeated;
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }
        public String getTaskTitle() { return taskTitle; }
        public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }
        public boolean isRepeated() { return repeated; }
        public void setRepeated(boolean repeated) { this.repeated = repeated; }

        /**
         * Constructor.
         * @param date
         * @param taskTitle
         * @param repeated
         */
        public Calendar(Date date, String taskTitle, boolean repeated) {
            this.date = date;
            this.taskTitle = taskTitle;
            this.repeated = repeated;
        }
    }

    /**
     * Prepare data for TableView.
     */
    private void initData() {
        Date date1 = java.util.Date.from(datePicker1.getValue().
                                         atStartOfDay().
                                         atZone(ZoneId.systemDefault()).
                                         toInstant());
        Date date2 = java.util.Date.from(datePicker2.getValue().
                                         atStartOfDay().
                                         atZone(ZoneId.systemDefault()).
                                         toInstant());
        calendarList.clear();
        for (SortedMap.Entry<Date, Set<Task>> calendar: (model.calendarList(date1, date2)).entrySet()) {
            for (Task task: calendar.getValue()) {
                Calendar element = new Calendar(calendar.getKey(), task.getTitle() , task.isRepeated());
                calendarList.add(element);
            }
        }
        // console version ***************
/*        for (SortedMap.Entry<Date, Set<Task>> mapList: (model.calendarList(date1, date2)).entrySet()) {
            for (Task task: mapList.getValue()) {
                System.out.println(mapList.getKey() + "\t" + task.getTitle());
            }
        }
*/
    }

    /**
     * Binding data with TableView.
     */
     void showTableAll() {
        // preparation data for tableView
        // this could be database
        initData();
        // set type and value, have to be contained in the column
        timeColumn.setCellValueFactory(new PropertyValueFactory<Calendar, Date>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Calendar, String>("taskTitle"));
        repeatedColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("repeated"));
        // fill table with data
        tableView.setItems(calendarList);
        logger.info("calendar timetable was shown successfully.");
    }

    @FXML
    void initialize() {

        datePicker1.setValue(LocalDate.now());
        datePicker2.setValue(LocalDate.now().plusDays(7));

        logger.info("CalendarWindow initialized.");
    }
}
