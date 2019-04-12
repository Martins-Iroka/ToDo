package com.martdev.android.todo.viewmodel.ToDoAddEditModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martdev.android.todo.repository.ToDoRepository;
import com.martdev.android.todo.repository.model.ToDo;

public class ToDoAddEditViewModel extends ViewModel {

    private ToDoRepository mRepository;

    ToDoAddEditViewModel(ToDoRepository repository) {
        mRepository = repository;
    }

    public LiveData<ToDo> getToDo(int id) {
        return mRepository.getToDo(id);
    }

    public void insertToDo(ToDo toDo) {
        mRepository.insertToDo(toDo);
    }

    public void updateToDo(ToDo toDo) {
        mRepository.updateToDo(toDo);
    }

}
