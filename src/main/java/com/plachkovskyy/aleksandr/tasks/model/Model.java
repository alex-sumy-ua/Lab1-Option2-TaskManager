package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.MyException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Model {

    final static Logger logger = Logger.getLogger(Model.class);

    static  String   filename = "tasklist.txt";
    private TaskList tasks;
    private File     file;
    private String   mode;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setTasks(TaskList taskList) {
        this.tasks = taskList;
    }

    /**
     * Getting task-list for controller if it is needed
     * @return TaskList
     */
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Model's constructor
     */
    public Model() {
        tasks = new ArrayTaskList();
        file  = new File(filename);
    }

    // Read current file
    public void readFile() {
        if (file.exists()) {
            try {
                TaskIO.readText(tasks, file);
                logger.info("task list initialized successfully.");
            } catch (IOException e) {
                //e.printStackTrace();
                logger.error("wrong tasks list initializing. " + e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("wrong tasks list parsing. " + e.getMessage());
            } catch (MyException e) {
                e.printStackTrace();
                logger.error("wrong tasks list initializing or parsing. " + e.getMessage());
            }
        } else {
            createFile(filename);
        }
    }

    // Load new file
    public void changeFile(String filename){
        if (file.exists()) {
            file.delete();
            logger.info("old current file was deleted.");
        }
        this.filename = filename;
        file = new File(filename);
        try {
            TaskIO.readText(tasks, file);
            logger.info("new task list loaded successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("file does not exist. " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("wrong tasks list loading. " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("wrong tasks list parsing. " + e.getMessage());
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("wrong tasks list loading or parsing. " + e.getMessage());
        }
    }

    // Save file onto the disk
    public void saveFile(String filename) {
        this.filename = filename;
        file = new File(filename);
        try {
            TaskIO.writeText(tasks, file);
            logger.info("current file was written successfully. ");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("wrong file saving.");
        }
    }

    // Create new file
    public void createFile(String filename) {
        if (file.exists()) {
            file.delete();
            logger.info("old current file was deleted.");
        }
        this.filename = filename;
        file = new File(filename);
        try {
            TaskIO.writeText(tasks, file);
            logger.info("file didn't exist and new file was created successfully. " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("wrong new empty file creation.");
        }
    }

    public SortedMap intervalList(int interval) { // period // may not needed
        Date date1 = new Date(System.currentTimeMillis());
        Date date2 = new Date(date1.getTime() + (interval * 86_400));
        return ( Tasks.calendar(tasks, date1, date2));
    }

    public Iterable<Task> incomingList(Date date1, Date date2) {
        Iterable<Task> list = null;
        try {
            list = Tasks.incoming(tasks, date1, date2);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<Date, Set<Task>> calendarList(Date start, Date end) {
        return Tasks.calendar(tasks, start, end);
    }

    /**
     * remove specific task in list
     * @param index number in list
     */
    public void remove(int index) {
        try {
            tasks.remove(tasks.getTask(index));
            logger.info("task removed successfully.");
            saveTasks();
        } catch (Exception e){
            logger.error("wrong task removing. " + e.getMessage());
        }
    }

    /**
     * controller specific task in list more detail
     * @param index number in list
     * @return Task
     */
    public Task getTask(int index) {
        return tasks.getTask(index);
    }

    /**
     * add task in list
     * @param task of type Task
     */
    public void addTask(Task task) {
        try {
            tasks.add(task);
            logger.info("task was added or edited successfully.");
//            saveTasks(); // will be done at the end of application running
        } catch (Exception e) {
            logger.error("wrong task adding. " + e.getMessage());
        }
    }

    /**
     * method for save list in file
     */
    private void saveTasks() {
        try {
            TaskIO.writeText(tasks, file);
            logger.info("file was written successfully.");
        } catch (Exception e){
            logger.error("wrong writing into file. " + e.getMessage());
        }
    }

}
