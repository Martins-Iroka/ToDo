package com.martdev.android.todo.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.martdev.android.todo.repository.dao.ToDoDao;
import com.martdev.android.todo.repository.dateConverter.DateConverter;
import com.martdev.android.todo.repository.model.ToDo;

@Database(entities = {ToDo.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDao todoDao();

    private static final String DATABASE_NAME = "todo_database";

    private static final Object LOCK = new Object();
    private static volatile ToDoDatabase sDatabase;

    public static ToDoDatabase getInstance(Context context) {
        if (sDatabase == null) {
            synchronized (LOCK) {
                if (sDatabase == null) {
                    sDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return sDatabase;
    }
}
