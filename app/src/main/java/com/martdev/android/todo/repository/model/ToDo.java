package com.martdev.android.todo.repository.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo_table")
public class ToDo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String whatToDo;
    private Date date;
    private boolean completed;

    public ToDo(String whatToDo, Date date, boolean completed) {
        this.whatToDo = whatToDo;
        this.date = date;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhatToDo() {
        return whatToDo;
    }

    public Date getDate() {
        return date;
    }

    public boolean isCompleted() {
        return completed;
    }
}
