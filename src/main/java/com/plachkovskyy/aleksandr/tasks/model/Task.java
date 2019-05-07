package com.plachkovskyy.aleksandr.tasks.model;


import com.plachkovskyy.aleksandr.tasks.exceptions.MyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class Task for some real task.
 */
public class Task implements Cloneable, Serializable {

    private String  title;      // name of task
    private Date    time;       // time of task without of repeat
    private Date    start;      // start-time of task with repeat
    private Date    end;        // end-time of task with repeat
    private int     interval;   // interval-time of task with repeat // Unit is a second!!!
    private boolean active;     // the task is active right now
    private boolean repeated;   // the task is repeated


//    private /*final*/ StringProperty observableTitle;

//    public StringProperty getObservableTitle() {
//        return observableTitle;
//    }

//    public void setObservableTitle(StringProperty observableTitle) {
//        this.observableTitle = observableTitle;
//    }

    /**
     * Default Constructor: empty task.
     * @param
     */
    public  Task() {
        this.title      =   null;
//        this.observableTitle = new SimpleStringProperty();
        this.time       =   null;
        this.start      =   null;
        this.end        =   null;
        this.interval   =   0;
        this.active     =   false;
        this.repeated   =   false;
    }

    /**
     * Constructor1: inactive Task with parameters.
     */
    public  Task(String title, Date time) {
        this.title      =   title;
//        this.observableTitle = new SimpleStringProperty(title);
        this.time       =   time;
        this.start      =   null;
        this.end        =   null;
        this.interval   =   0;
        this.active     =   true;
        this.repeated   =   false;
    }

    /**
     * Constructor2 of the Task with parameters.
     */
    public  Task(String title, Date start, Date end, int interval)
            throws MyException {
        if (start == null || end == null)
            throw new MyException("Start and End cannot be null!");
        if (interval <= 0)
            throw new MyException("Interval must be more then zero!");
        if (end.before(start))
            throw new MyException("End-time cannot be less then start-time!");
        this.title      =   title;
//        this.observableTitle = new SimpleStringProperty(title);
        this.time       =   null;
        this.start      =   start;
        this.end        =   end;
        this.interval   =   interval;
        this.active     =   true;
        this.repeated   =   true;
    }

    /**
     * Get title method.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title method.
     */
    public void setTitle(String title) throws MyException {
        if ((title != null) && (!title.equals("")))
            this.title = title;
        else throw new MyException("Title mustn't be empty!");
    }

    /**
     * Get active method.
     */
    public boolean isActive() {
        return active;

    }

    /**
     * Set active method.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * If repeated, return start-time.
     */
    public Date getTime() {
        if (!repeated) return time;
        else return start;
    }

    /**
     * If repeated, make it nonrepeated.
     */
    public void setTime(Date time) {
        repeated        =   false;
        this.time       =   time;
    }

    /**
     * If nonrepeated, return time.
     */
    public Date getStart() {
        if (repeated) return start;
        else return time;
    }

    /**
     * If nonrepeated, return time.
     */
    public Date getEnd() {
        if (repeated) return end;
        else return time;
    }

    /**
     * If nonrepeated, return 0.
     */
    public int getInterval() {
        if (repeated) return interval;
        else return 0;
    }

    /**
     * If nonrepeated, make it repeated.
     */
    public void setTime(Date start, Date end, int interval) throws Exception {
        if (start == null || end == null)
            throw new Exception("Start and End cannot be null!");
        if (interval <= 0)
            throw new Exception("Interval must be more then zero!");
        if (end.before(start))
            throw new Exception("End-time cannot be less then start-time!");
        this.start      =   start;
        this.end        =   end;
        this.interval   =   interval;
        if (!repeated) repeated = true;
    }

    /**
     * If it is repeated.
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * If possible, return next time or start, or return -1, if impossible.
     */
    public Date nextTimeAfter(Date current) {
        if (!active) return null;
        if (!repeated) {
            if (current.after(time) || current.equals(time)) return null;
            return new Date(time.getTime());
        }
        if (current.after(end)) return null;
        if (start.after(current)) return new Date(start.getTime());
        Date next = new Date(start.getTime());
        do {
            next.setTime(next.getTime() + interval * 1_000);
        } while (next.before(current));
        if (current.equals(next)) next.setTime(next.getTime() + interval * 1_000);
        if (next.after(end)) return null;
        return next;
    }

    /*
     * Redefining of method equals().
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null || this.getClass() != otherObject.getClass()) return false;
        Task other = (Task) otherObject;
        if (!isRepeated()) {
            return  !other.isRepeated() &&
                    this.hashCode() == other.hashCode() &&
                    this.title.equalsIgnoreCase(other.title) &&
                    Boolean.compare(this.isActive(), other.isActive()) == 0 &&
                    this.time.compareTo(other.time) == 0;
        } else {
            return  other.isRepeated() &&
                    this.hashCode() == other.hashCode() &&
                    this.title.equals(other.title) &&
                    Boolean.compare(this.isActive(), other.isActive()) == 0 &&
                    this.start.compareTo(other.start) == 0 &&
                    this.end.compareTo(other.end) == 0 &&
                    this.interval == other.interval;
        }
    }

    /*
     * Redefining of method hashCode().
     */
    @Override
    public int hashCode() {
        final int prime = 1_113;
        int result = 1;
        result = prime * result + (this.isRepeated() ? 0 : prime);
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        result = prime * result + (this.isActive() ? 0 : prime);
        if (!isRepeated()) {
            result = prime * result + this.time.hashCode();
        } else {
            result = prime * result + this.start.hashCode();
            result = prime * result + this.end.hashCode();
            result = prime * result + this.interval;
        }
        return result;
    }

    /*
     * Redefining of method toString().
     */
    @Override
    public String toString() {
        String stringDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringDateFormat);
        //simpleDateFormat.format(time).toString();
        StringBuffer strbuf = new StringBuffer("\"");
        strbuf.append(this.title);
        strbuf.append("\"");
        if (!repeated) {
            strbuf.append(" at [");
            strbuf.append(simpleDateFormat.format(this.time).toString());
            strbuf.append("]");
        } else {
            strbuf.append(" from [");
            strbuf.append(simpleDateFormat.format(this.start).toString());
            strbuf.append("] to [");
            strbuf.append(simpleDateFormat.format(this.end).toString());
            strbuf.append("] every [");
            {
                int days = interval / 86_400;
                int hours = (interval - days * 86_400) / 3_600;
                int minutes = (interval - days * 86_400 - hours * 3_600) / 60;
                int seconds = interval - days * 86_400 - hours * 3_600 - minutes * 60;
                StringBuilder strInterval = new StringBuilder();
                if (days != 0) {
                    strInterval.append(" ").append(days).append((days > 1 ? " days" : " day"));
                }
                if (hours != 0) {
                    strInterval.append(" ").append(hours).append((hours > 1 ? " hours" : " hour"));
                }
                if (minutes != 0) {
                    strInterval.append(" ").append(minutes).append((minutes > 1 ? " minutes" : " minute"));
                }
                if (seconds != 0) {
                    strInterval.append(" ").append(seconds).append((seconds > 1 ? " seconds" : " second"));
                }
                strbuf.append(strInterval);
            }
            strbuf.append("]");
        }
        if (!active) {
            strbuf.append(" inactive");
        }
        return strbuf.toString();
    }

    /*
     * Redefining of method clone().
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }

}
