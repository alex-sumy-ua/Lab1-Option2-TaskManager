package com.plachkovskyy.aleksandr.tasks.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import com.plachkovskyy.aleksandr.tasks.exceptions.MyException;
import com.plachkovskyy.aleksandr.tasks.model.Model;
import com.plachkovskyy.aleksandr.tasks.model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

public class AddEditTaskWindow {

    final static Logger logger = Logger.getLogger(CalendarWindowController.class);

    private Model model;
    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }

    private boolean deletable;
    public boolean getDeletable() {return deletable; }
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
        deleteButton.setDisable(!deletable);
    }

    private MainListWindowController mainListWindowController;
    public MainListWindowController getMainListWindowController() { return mainListWindowController; }
    public void setMainListWindowController(MainListWindowController mainListWindowController) {
        this.mainListWindowController = mainListWindowController; }

    private Task task;
    public Task getTask() throws MyException {
        Task task;
        if (nonRepeatedRadioButton.isSelected()) {
            // nonRepeated task
            Date at = java.util.Date.from(atDatePicker.getValue().
                                              atStartOfDay().
                                              atZone(ZoneId.systemDefault()).
                                              toInstant());
            at.setHours(Integer.parseInt(atHours.getText()));
            at.setMinutes(Integer.parseInt(atMinutes.getText()));
            at.setSeconds(0);
            task = new Task(titleField.getText(), at);
        } else {
            //repeated tsak
            Date start = java.util.Date.from(fromDatePicker.getValue().
                                             atStartOfDay().
                                             atZone(ZoneId.systemDefault()).
                                             toInstant());
            start.setHours(Integer.parseInt(fromHours.getText()));
            start.setMinutes(Integer.parseInt(fromMinutes.getText()));
            start.setSeconds(0);
            Date end = java.util.Date.from(toDatePicker.getValue().
                                           atStartOfDay().
                                           atZone(ZoneId.systemDefault()).
                                           toInstant());
            end.setHours(Integer.parseInt(toHours.getText()));
            end.setMinutes(Integer.parseInt(toMinutes.getText()));
            end.setSeconds(0);
            int interval = Integer.parseInt(intervalDays.getText()) * 86_400 +
                           Integer.parseInt(intervalHours.getText()) * 3_600 +
                           Integer.parseInt(intervalMinutes.getText()) * 60;
            task = new Task(titleField.getText(), start, end, interval);
        }
        task.setActive(activeCheckBox.isSelected());
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
        titleField.setText(this.task.getTitle());
        activeCheckBox.setSelected(this.task.isActive());
        if (this.task.isRepeated()) {
            setRepeated();
            fromDatePicker.setValue(new java.sql.Date(this.task.getStart().getTime()).toLocalDate());
            toDatePicker.setValue(new java.sql.Date(this.task.getEnd().getTime()).toLocalDate());
            fromHoursSlider.setValue(new Long(this.task.getStart().getHours()));
            fromMinutesSlider.setValue(new Long(this.task.getStart().getMinutes()));
            toHoursSlider.setValue(new Long(this.task.getEnd().getHours()));
            toMinutesSlider.setValue(new Long(this.task.getEnd().getMinutes()));
            intervalDaysSlider.setValue(new Long(this.task.getInterval() / 86_400));
            intervalHoursSlider.setValue(new Long(this.task.getInterval() % 86_400 /  3_600));
            intervalMinutesSlider.setValue(new Long(this.task.getInterval() % 86_400 % 3_600 / 60));
        }
        else {
            setNonRepeated();
            atDatePicker.setValue(new java.sql.Date(this.task.getTime().getTime()).toLocalDate());
            atHoursSlider.setValue(new Long(this.task.getTime().getHours()));
           // atHours.setText( new Integer(this.task.getTime().getHours()).toString() ); // for example
            atMinutesSlider.setValue(new Long(this.task.getTime().getMinutes()));
        }
    }

    private boolean add_NotEdit;
    public boolean isAdd_NotEdit() { return add_NotEdit; }
    public void setAdd_NotEdit(boolean add_NotEdit) {
        this.add_NotEdit = add_NotEdit;
    }

    public AddEditTaskWindow() {
        this.task = new Task();
        this.model = new Model();
        this.add_NotEdit = true;
        this.mainListWindowController = new MainListWindowController();
    }

    public void setActiveCheckBox(boolean active) {
        activeCheckBox.setSelected(active);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField titleField;

    @FXML
    private TextField atHours;

    @FXML
    private TextField atMinutes;

    @FXML
    private TextField fromHours;

    @FXML
    private TextField fromMinutes;

    @FXML
    private TextField toHours;

    @FXML
    private TextField toMinutes;

    @FXML
    private TextField intervalDays;

    @FXML
    private TextField intervalHours;

    @FXML
    private TextField intervalMinutes;

    @FXML
    private DatePicker atDatePicker;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Slider atHoursSlider;

    @FXML
    private Slider atMinutesSlider;

    @FXML
    private Slider fromHoursSlider;

    @FXML
    private Slider fromMinutesSlider;

    @FXML
    private Slider toHoursSlider;

    @FXML
    private Slider toMinutesSlider;

    @FXML
    private Slider intervalDaysSlider;

    @FXML
    private Slider intervalHoursSlider;

    @FXML
    private Slider intervalMinutesSlider;

    @FXML
    private RadioButton nonRepeatedRadioButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private RadioButton repeatedRadioButton;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    void onSaveButtonPressed(ActionEvent event) {
        if (titleField.getText().isEmpty()) {
            logger.error("task title cannot be empty. Task was not saved.");
            return;
        }
        if (isAdd_NotEdit()) {
            // new task
            try {
                model.addTask(getTask());
            } catch (MyException e) {
                e.printStackTrace();
            }
        } else {
            // edit current task = remove current task + add current changed task
            try {
                model.getTasks().remove(this.task);
            } catch (MyException e) {
                e.printStackTrace();
            }
            try {
                model.addTask(getTask());
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        mainListWindowController.initData();
        saveButton.getScene().getWindow().hide();
    }

    @FXML
    void onDeleteButtonPressed(ActionEvent event) {
        try {
            model.getTasks().remove(task);
            logger.info("task was deleted successfully.");
        } catch (MyException e) {
            logger.error("fail deleting task. " + e.getMessage());
        }
        mainListWindowController.initData();
        deleteButton.getScene().getWindow().hide();
    }

    @FXML
    void onActiveCheckBoxChecked (ActionEvent event) {

    }

    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        logger.info("AddEditTaskWindow was closed.");
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    void onNonRepeatedRadioButton(ActionEvent event) {
        setNonRepeated();
    }

    public void setNonRepeated() {
        nonRepeatedRadioButton.setSelected(true);
        repeatedRadioButton.setSelected(!nonRepeatedRadioButton.selectedProperty().getValue());
        nonRepeatedBlockSetEnabled(true);
        repeatedBlockSetEnabled(false);
    }

    @FXML
    void onRepeatedRadioButton(ActionEvent event) {
        setRepeated();
    }

    public void setRepeated() {
        repeatedRadioButton.setSelected(true);
        nonRepeatedRadioButton.setSelected(!repeatedRadioButton.selectedProperty().getValue());
        repeatedBlockSetEnabled(true);
        nonRepeatedBlockSetEnabled(false);
    }

    void nonRepeatedBlockSetEnabled(boolean enable) {
        fromDatePicker.setDisable(enable);
        fromHoursSlider.setDisable(enable);
        fromMinutesSlider.setDisable(enable);
        toDatePicker.setDisable(enable);
        toHoursSlider.setDisable(enable);
        toMinutesSlider.setDisable(enable);
        fromHours.setDisable(enable);
        fromMinutes.setDisable(enable);
        toHours.setDisable(enable);
        toMinutes.setDisable(enable);
        intervalDays.setDisable(enable);
        intervalHours.setDisable(enable);
        intervalMinutes.setDisable(enable);
        intervalDaysSlider.setDisable(enable);
        intervalHoursSlider.setDisable(enable);
        intervalMinutesSlider.setDisable(enable);
        atDatePicker.setDisable(!enable);
        atHoursSlider.setDisable(!enable);
        atMinutesSlider.setDisable(!enable);
        atHours.setDisable(!enable);
        atMinutes.setDisable(!enable);
    }

    void repeatedBlockSetEnabled(boolean enable) {
        fromDatePicker.setDisable(!enable);
        fromHoursSlider.setDisable(!enable);
        fromMinutesSlider.setDisable(!enable);
        toDatePicker.setDisable(!enable);
        toHoursSlider.setDisable(!enable);
        toMinutesSlider.setDisable(!enable);
        fromHours.setDisable(!enable);
        fromMinutes.setDisable(!enable);
        toHours.setDisable(!enable);
        toMinutes.setDisable(!enable);
        intervalDays.setDisable(!enable);
        intervalHours.setDisable(!enable);
        intervalMinutes.setDisable(!enable);
        intervalDaysSlider.setDisable(!enable);
        intervalHoursSlider.setDisable(!enable);
        intervalMinutesSlider.setDisable(!enable);
        atDatePicker.setDisable(enable);
        atHoursSlider.setDisable(enable);
        atMinutesSlider.setDisable(enable);
        atHours.setDisable(enable);
        atMinutes.setDisable(enable);
    }

    // onCloseWindow ***************************************************************************************************
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {//*
        @Override                                                                                                    //*
        public void handle(WindowEvent event) {                                                                      //*
            onCancelButtonPressed(new ActionEvent());                                                                //*
        }                                                                                                            //*
    };                                                                                                               //*
                                                                                                                     //*
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){                                            //*
        return closeEventHandler;                                                                                    //*
    }                                                                                                                //*
    //******************************************************************************************************************

    @FXML
    void initialize() {
        atHours.setText("0");
        atMinutes.setText("0");
        fromHours.setText("0");
        fromMinutes.setText("0");
        toHours.setText("0");
        toMinutes.setText("0");
        intervalDays.setText("0");
        intervalHours.setText("0");
        intervalMinutes.setText("0");
        atDatePicker.setValue(LocalDate.now());
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now().plusDays(7));
        nonRepeatedRadioButton.setSelected(true);
        nonRepeatedBlockSetEnabled(true);
        repeatedBlockSetEnabled(false);

        // atHoursSlider Listener
        atHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                atHours.setText(String.format("%.0f", new_val));
            }
        });

        // atMinutesSlider Listener
        atMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                atMinutes.setText(String.format("%.0f", new_val));
            }
        });

        // fromHoursSlider Listener
        fromHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                fromHours.setText(String.format("%.0f", new_val));
            }
        });

        // fromMinutesSlider Listener
        fromMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                fromMinutes.setText(String.format("%.0f", new_val));
            }
        });

        // toHoursSlider Listener
        toHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                toHours.setText(String.format("%.0f", new_val));
            }
        });

        // toMinutesSlider Listener
        toMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                toMinutes.setText(String.format("%.0f", new_val));
            }
        });

        // intervalDaysSlider Listener
        intervalDaysSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                intervalDays.setText(String.format("%.0f", new_val));
            }
        });

        // intervalHoursSlider Listener
        intervalHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                intervalHours.setText(String.format("%.0f", new_val));
            }
        });

        // intervalMinutesSlider Listener
        intervalMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                intervalMinutes.setText(String.format("%.0f", new_val));
            }
        });

        logger.info("AddEditTaskWindow initialized.");

    }

}
