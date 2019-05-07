package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TaskIO {

    /**
     * Записує задачі із списку у потік у бінарному форматі, описаному нижче.
     * @param tasks
     * @param out
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        Iterator<Task> iterator = tasks.iterator();
        DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(out));
        try {
            dataOutStream.writeInt(tasks.size());           // tasks quantity
            while (iterator.hasNext()) {
                Task task = (Task) iterator.next();
                dataOutStream.writeUTF(task.getTitle());    // task title
                dataOutStream.writeBoolean(task.isActive());
                if (task.isRepeated()) {
                    dataOutStream.writeLong(task.getStart().getTime());
                    dataOutStream.writeUTF(" ");
                    dataOutStream.writeLong(task.getEnd().getTime());
                    dataOutStream.writeInt(task.getInterval());
                }
                else {
                    dataOutStream.writeLong(task.getTime().getTime());
                }
                dataOutStream.writeUTF("\n");
            }
        } finally {
            dataOutStream.close();
        }

    }

    /**
     * Зчитує задачі із потоку у даний список задач.
     * @param tasks
     * @param in
     */
    public static void read(TaskList tasks, InputStream in) throws IOException, MyException {
        String title;
        boolean isActive;
        Date time = new Date();
        Date startTime = new Date();
        Date endTime = new Date();
        int interval;
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(in));
        try {
            int tasksQuantity = dataInputStream.readInt();
            for (int i = 0; i < tasksQuantity; i++) {
                title = dataInputStream.readUTF();
                isActive = dataInputStream.readBoolean();
                Date tmpTime = new Date();
                tmpTime.setTime(dataInputStream.readLong());
                if (dataInputStream.readUTF().equals(" ")) {// task isRepeated
                    startTime = tmpTime;
                    endTime.setTime(dataInputStream.readLong());
                    interval = dataInputStream.readInt();
                    Task task = new Task(title, startTime, endTime, interval);
                    task.setActive(isActive);
                    dataInputStream.readUTF();              // "\n"
                    tasks.add(task);
                } else {                                    // task ! isRepeated
                    time = tmpTime;
                    Task task = new Task(title, time);
                    task.setActive(isActive);
                    tasks.add(task);
                }
            }
        } finally {
            dataInputStream.close();
        }
    }

    /**
     * Записує задачі із списку у файл.
     * @param tasks
     * @param file
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            write(tasks, fileOutputStream);
        } finally {
            fileOutputStream.close();
        }
    }

    /**
     * Зчитує задачі із файлу у список задач.
     * @param tasks
     * @param file
     */
    public static void readBinary(TaskList tasks, File file) throws IOException, MyException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            read(tasks, fileInputStream);
        } finally {
            fileInputStream.close();
        }
    }

    /**
     * Converter int interval value to String.
     * @param interval
     * @return String
     */
    public static String intToStringInterval (int interval) {
        int days = interval / 86_400;
        int hours = (interval - days * 86_400) / 3_600;
        int minutes = (interval - days * 86_400 - hours * 3_600) / 60;
        int seconds = interval - days * 86_400 - hours * 3_600 - minutes * 60;
        StringBuilder strInterval = new StringBuilder();
        if (days != 0) {
            strInterval.append(days).append((days > 1 ? " days" : " day"));
        }
        if (hours != 0) {
            strInterval.append(hours).append((hours > 1 ? " hours" : " hour"));
        }
        if (minutes != 0) {
            strInterval.append(minutes).append((minutes > 1 ? " minutes" : " minute"));
        }
        if (seconds != 0) {
            strInterval.append(seconds).append((seconds > 1 ? " seconds" : " second"));
        }
        return strInterval.toString();
    }

    /**
     * Converter String interval to int value.
     * @param interval
     * @return String
     */
    public static int stringToIntInterval (String interval) {
        int intInterval = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if (interval.contains("day")) {
            days = Integer.parseInt(interval.substring(0, interval.indexOf("day") - 1).trim());
            interval = interval.substring(interval.indexOf("day") + 4, interval.length()).trim();
        }
        if (interval.contains("hour")) {
            hours = Integer.parseInt(interval.substring(0, interval.indexOf("hour") - 1).trim());
            interval = interval.substring(interval.indexOf("hour") + 5, interval.length()).trim();
        }
        if (interval.contains("minute")) {
            minutes = Integer.parseInt(interval.substring(0, interval.indexOf("minute") - 1).trim());
            interval = interval.substring(interval.indexOf("minute") + 7, interval.length()).trim();
        }
        if (interval.contains("second")) {
            seconds = Integer.parseInt(interval.substring(0, interval.length() - 7).trim());
        }
        intInterval = days * 86_400 + hours * 3_600 + minutes * 60 + seconds;
        return intInterval;
    }

//******************************************************************************

    /**
     * Записує задачі зі списку у потік в текстовому форматі, описаному нижче.
     * @param tasks
     * @param out
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
        Iterator<Task> iterator = tasks.iterator();
        StringBuilder taskList = new StringBuilder();
        while (iterator.hasNext()) {
            taskList.append(iterator.next().toString());
            if (iterator.hasNext()){
                taskList.append(";\n");
            } else {
                taskList.append('.');
            }
        }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(out);
            printWriter.write(taskList.toString());
        } finally {
            if(printWriter != null){
                printWriter.close();
            }
        }
/*        Iterator<Task> iterator = tasks.iterator();
        BufferedWriter bufferedWriter = new BufferedWriter(out);
        try {
            while (iterator.hasNext()) {
                Task task = iterator.next();
                bufferedWriter.write("\"" + task.getTitle() + "\"");
                if (task.isRepeated()) {
                    bufferedWriter.write(" from [" + TaskIO.timeToString(task.getStart()) + "]");
                    bufferedWriter.write(" to [" + TaskIO.timeToString(task.getEnd()) + "]");
                    bufferedWriter.write(" every [" + String.valueOf(task.getInterval()) + "]");
                } else {
                    bufferedWriter.write(" at [" + TaskIO.timeToString(task.getTime()) + "]");
                }
                if (task.isActive()) { bufferedWriter.write(" active"); }
                else { bufferedWriter.write(" inactive"); }
                if (iterator.hasNext()) { bufferedWriter.write(";\n"); }
                else { bufferedWriter.write("."); }
            }
        } finally {
            bufferedWriter.close();
        }
*/
    }

    /**
     * Зчитує задачі із потоку у список.
     * @param tasks
     * @param in
     */
    public static void read(TaskList tasks, Reader in) throws IOException, ParseException, MyException {

        String textTaskReader;
        String title;
        Date time;
        Date startTime;
        Date endTime;
        boolean isActive;
        int interval;

        try(BufferedReader bufferedReader = new BufferedReader(in)) {
            while ((textTaskReader = bufferedReader.readLine()) != null) {
                title = textTaskReader.substring(1, textTaskReader.indexOf("\"", 1));
                if (textTaskReader.contains("inactive")) { isActive = false; }
                else { isActive = true; }
                if (textTaskReader.contains("every")) {
                    startTime = stringToTime(textTaskReader.substring((textTaskReader.indexOf("[") + 1),
                                                                       textTaskReader.indexOf("[") + 19));//23/25
                    endTime = stringToTime(textTaskReader.substring((textTaskReader.indexOf("[") + 26),//30//31
                                                                     textTaskReader.indexOf("[") + 44));//52//55
                    interval = stringToIntInterval(textTaskReader.substring((textTaskReader.lastIndexOf("every")+7),
                                                                             textTaskReader.lastIndexOf("]")));//0
                    Task task = new Task(title, startTime, endTime, interval);
                    task.setActive(isActive);
                    tasks.add(task);
                } else {
                    time = stringToTime(textTaskReader.substring((textTaskReader.indexOf("[") + 1),
                                                                  textTaskReader.indexOf("[") + 19));//30//25
                    Task task = new Task(title, time);
                    task.setActive(isActive);
                    tasks.add(task);
                }
            }
        }/* finally {       // not needed to close incoming parameter in
            in.close();
        }*/
    }

    /**
     * Записує задачі у файл у текстовому форматі.
     * @param tasks
     * @param file
     */
    public static void writeText(TaskList tasks, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        try {
            write(tasks, fileWriter);
        } finally {
            fileWriter.close();
        }
    }

    /**
     * Зчитує задачі із файлу у текстовому вигляді.
     * @param tasks
     * @param file
     */
    public static void readText(TaskList tasks, File file) throws IOException, ParseException, MyException {
        FileReader fileReader = new FileReader(file);
        try {
            read(tasks, fileReader);
        } finally {
            fileReader.close();
        }
    }

    /**
     * Additional method for parsing Date time to String-format "yyyy-MM-dd HH:mm:ss.SSS"
     * @param time
     * @return timeToString
     */
    public static String timeToString(Date time){
        String stringDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringDateFormat);
        return simpleDateFormat.format(time).toString();
    }

    /**
     * Additional method for parsing String-format "yyyy-MM-dd HH:mm:ss.SSS" to  Date
     * @param stringTime
     * @return stringToTime
     */
    public static Date stringToTime (String stringTime) throws ParseException {
        String stringDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringDateFormat);
        return simpleDateFormat.parse(stringTime);
    }

}
