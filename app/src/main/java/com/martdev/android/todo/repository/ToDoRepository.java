package com.martdev.android.todo.repository;

import android.arch.lifecycle.LiveData;

import com.martdev.android.todo.utils.AppExecutor;
import com.martdev.android.todo.repository.dao.ToDoDao;
import com.martdev.android.todo.repository.model.ToDo;

import java.util.List;

public class ToDoRepository {

    private static final Object LOCK = new Object();
    private static ToDoRepository sRepository;
    private ToDoDao mDao;
    private AppExecutor mExecutor;

    private ToDoRepository(ToDoDao dao, AppExecutor executor) {
        mDao = dao;
        mExecutor = executor;
    }

    public synchronized static ToDoRepository getRepository(ToDoDao dao,
                                                            AppExecutor executor) {
        if (sRepository == null) {
            synchronized (LOCK) {
                sRepository = new ToDoRepository(dao, executor);
            }
        }
        return sRepository;
    }

    public void insertToDo(final ToDo toDo) {
        mExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.insert(toDo);
            }
        });
    }

    public void updateToDo(final ToDo toDo) {
        mExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.update(toDo);
            }
        });
    }

    public LiveData<List<ToDo>> getToDo() {
        return mDao.getToDo();
    }

    public LiveData<ToDo> getToDo(final int id) {
        return mDao.getToDoById(id);
    }

    public void deleteToDoById(final int id) {
        mExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteToDoById(id);
            }
        });
    }
}
