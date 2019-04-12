package com.martdev.android.todo.viewmodel.ToDoAddEditModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.martdev.android.todo.repository.ToDoRepository;

public class ToDoAddEditModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ToDoRepository mRepository;

    public ToDoAddEditModelFactory(ToDoRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ToDoAddEditViewModel(mRepository);
    }
}
