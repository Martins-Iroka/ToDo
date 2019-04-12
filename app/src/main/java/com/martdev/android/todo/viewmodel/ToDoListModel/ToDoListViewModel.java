package com.martdev.android.todo.viewmodel.ToDoListModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martdev.android.todo.repository.ToDoRepository;
import com.martdev.android.todo.repository.model.ToDo;

import java.util.List;

public class ToDoListViewModel extends ViewModel {

    private final ToDoRepository mRepository;

    ToDoListViewModel(ToDoRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<ToDo>> getTodoList() {
        return mRepository.getToDo();
    }

    public void deleteToDo(ToDo toDo) {
        mRepository.deleteToDoById(toDo.getId());
    }
}
