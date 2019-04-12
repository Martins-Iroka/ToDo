package com.martdev.android.todo.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Object LOCK = new Object();
    private static AppExecutor sExecutor;
    private final Executor diskIO;

    private AppExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    static AppExecutor getInstance() {
        if (sExecutor == null) {
            synchronized (LOCK) {
                sExecutor = new AppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sExecutor;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
