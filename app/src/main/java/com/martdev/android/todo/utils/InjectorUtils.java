package com.martdev.android.todo.utils;

import android.content.Context;

import com.martdev.android.todo.repository.ToDoRepository;
import com.martdev.android.todo.repository.database.ToDoDatabase;
import com.martdev.android.todo.viewmodel.ToDoAddEditModel.ToDoAddEditModelFactory;
import com.martdev.android.todo.viewmodel.ToDoListModel.ToDoListModelFactory;

public class InjectorUtils {

    private static ToDoRepository provideRepository(Context context) {
        ToDoDatabase database = ToDoDatabase.getInstance(context.getApplicationContext());
        AppExecutor executor = AppExecutor.getInstance();
        return ToDoRepository.getRepository(database.todoDao(), executor);
    }

    public static ToDoAddEditModelFactory provideToDoAddEditModelFactory(Context context) {
        ToDoRepository repository = provideRepository(context.getApplicationContext());
        return new ToDoAddEditModelFactory(repository);
    }

    public static ToDoListModelFactory provideTodoListModelFactory(Context context) {
        ToDoRepository repository = provideRepository(context.getApplicationContext());
        return new ToDoListModelFactory(repository);
    }
}
