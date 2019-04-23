package com.plachkovskyy.aleksandr.tasks.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

public class AddEditTaskWindow {

    final static Logger logger = Logger.getLogger(CalendarController.class);
    //        logger.info("Logger testing - info");
    //        logger.error("Logger testing - error");

    private boolean deletable;
    public boolean getDeletable() {return deletable; }
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
        deleteButton.setDisable(!deletable);
    }



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private TextField atMinutes;

    @FXML
    private TextField fromMinutes;

    @FXML
    private TextField atHours;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Slider fromHoursSlider;

    @FXML
    private TextField intervalMinutes;

    @FXML
    private Slider fromMinutesSlider;

    @FXML
    private TextField fromHours;

    @FXML
    private Slider intervalHoursSlider;

    @FXML
    private Slider atHoursSlider;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker atDatePicker;

    @FXML
    private Slider toMinutesSlider;

    @FXML
    private TextField intervalDays;

    @FXML
    private Slider toHoursSlider;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Slider intervalMinutesSlider;

    @FXML
    private Slider atMinutesSlider;

    @FXML
    private TextField intervalHours;

    @FXML
    private TextField toMinutes;

    @FXML
    private TextField toHours;

    @FXML
    private RadioButton nonRepeatedRadioButton;

    @FXML
    private RadioButton repeatedRadioButton;

    @FXML
    private Slider intervalDaysSlider;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    void onAddButtonPressed(ActionEvent event) {
        //
        saveButton.getScene().getWindow().hide();

    }

    @FXML
    void onDeleteButtonPressed(ActionEvent event) {
        //
        deleteButton.getScene().getWindow().hide();
    }

    @FXML
    void onActiveCheckBoxChecked (ActionEvent event) {

    }


    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    void onNonRepeatedRadioButton(ActionEvent event) {
        nonRepeatedRadioButton.setSelected(true);
        repeatedRadioButton.setSelected(!nonRepeatedRadioButton.selectedProperty().getValue());
        nonRepeatedBlockSetEnabled(true);
        repeatedBlockSetEnabled(false);

    }

    @FXML
    void onRepeatedRadioButton(ActionEvent event) {
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
        //atHours.textProperty().bindBidirectional(atHoursSlider.valueProperty(), NumberFormat.getNumberInstance());

        // atHoursSlider Listener
        atHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                atHours.setText(String.format("%.0f", new_val));
            }
        });

        // atMinutesSlider Listener
        atMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                atMinutes.setText(String.format("%.0f", new_val));
            }
        });

        // fromHoursSlider Listener
        fromHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                fromHours.setText(String.format("%.0f", new_val));
            }
        });

        // fromMinutesSlider Listener
        fromMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                fromMinutes.setText(String.format("%.0f", new_val));
            }
        });

        // toHoursSlider Listener
        toHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                toHours.setText(String.format("%.0f", new_val));
            }
        });

        // toMinutesSlider Listener
        toMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                toMinutes.setText(String.format("%.0f", new_val));
            }
        });

        // intervalDaysSlider Listener
        intervalDaysSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                intervalDays.setText(String.format("%.0f", new_val));
            }
        });

        // intervalHoursSlider Listener
        intervalHoursSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                intervalHours.setText(String.format("%.0f", new_val));
            }
        });

        // intervalMinutesSlider Listener
        intervalMinutesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                //int current = new_val.intValue();
                intervalMinutes.setText(String.format("%.0f", new_val));
            }
        });




    }


}
