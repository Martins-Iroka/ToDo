package com.martdev.android.todo.viewmodel.ToDoListModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.martdev.android.todo.repository.ToDoRepository;

public class ToDoListModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ToDoRepository mRepository;

    public ToDoListModelFactory(ToDoRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ToDoListViewModel(mRepository);
    }
}
