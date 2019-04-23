package com.plachkovskyy.aleksandr.tasks.model;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.SortedMap;

public class Model {
    final static Logger logger = Logger.getLogger(Model.class);

    static  String   filename = "/resources/tasklist.txt";
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

    public Model() {
        try {
            tasks = new ArrayTaskList();
            file  = new File(filename);
            TaskIO.readText(tasks, file);
            logger.info("Logger: TaskList initialized successfully.");
        } catch (Exception e) {
            logger.error("Logger: wrong tasks list initialization. " + e.getMessage());
        }
    }

    public void changeFile(String filename){
        file.delete();
        this.filename = filename;
        file = new File(filename);
        saveTasks();
        try {
            TaskIO.writeText(tasks, file);
            logger.info("Logger: new file " + filename + " was created.");
        } catch (IOException e) {
            logger.error("Logger: wrong new file creating. " + e.getMessage());
        }
    }

    public SortedMap intervalList(int interval) { // period
        Date date1 = new Date(System.currentTimeMillis());
        Date date2 = new Date(date1.getTime() + (interval * 1_000 * 86_400));
        return ( Tasks.calendar(tasks, date1, date2));
    }

    /**
     * remove specific task in list
     * @param index number in list
     */
    public void remove(int index) {
        try {
            tasks.remove(tasks.getTask(index));
            logger.info("Logger: task removed successfully.");
            saveTasks();
        } catch (Exception e){
            logger.error("Logger: wrong task removing. " + e.getMessage());
        }
    }

    /**
     * view specific task in list more detail
     * @param index number in list
     * @return Task
     */
    public Task view(int index) {
        return tasks.getTask(index);
    }

    /**
     * add task in list
     * @param task of type Task
     */
    public void addTask(Task task) {
        try {
            tasks.add(task);
            logger.info("Logger: task added successfully.");
            saveTasks();
        } catch (Exception e) {
            logger.error("Logger: wrong task adding. " + e.getMessage());
        }
    }

    /**
     * method for save list in file
     */
    private void saveTasks() {
        try {
            TaskIO.writeText(tasks, file);
            logger.info("Logger: file wrote successfully.");
        } catch (Exception e){
            logger.error("Logger: wrong writing into file. " + e.getMessage());
        }
    }

}
