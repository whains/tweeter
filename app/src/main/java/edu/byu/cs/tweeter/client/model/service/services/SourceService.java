package edu.byu.cs.tweeter.client.model.service.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class SourceService<T extends Runnable> {

    public void executeMultiTask(T task1, T task2) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(task1);
        executor.execute(task2);
    }

    public void executeSingleTask(T task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(task);
    }
}
