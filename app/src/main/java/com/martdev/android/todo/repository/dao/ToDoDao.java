package com.martdev.android.todo.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.martdev.android.todo.repository.model.ToDo;

import java.util.List;

@Dao
public interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToDo toDo);

    @Update
    void update(ToDo toDo);

    @Query("SELECT * FROM todo_table")
    LiveData<List<ToDo>> getToDo();

    @Query("SELECT * FROM todo_table WHERE id = :id")
    LiveData<ToDo> getToDoById(int id);

    @Query("DELETE FROM todo_table WHERE id = :id")
    void deleteToDoById(int id);
}
