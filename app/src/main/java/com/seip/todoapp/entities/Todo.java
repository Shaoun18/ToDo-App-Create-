package com.seip.todoapp.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_todo")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String priority;
    private String date;
    private String time;
    private boolean completed;

    @Ignore
    public Todo(int id, String name, String priority, String date, String time, boolean completed) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.date = date;
        this.time = time;
        this.completed = completed;
    }

    public Todo(String name, String priority, String date, String time, boolean completed) {
        this.name = name;
        this.priority = priority;
        this.date = date;
        this.time = time;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}
