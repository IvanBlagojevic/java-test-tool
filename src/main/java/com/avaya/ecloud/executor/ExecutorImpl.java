package com.avaya.ecloud.executor;


import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class ExecutorImpl implements Executor {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public ScheduledExecutorService getExecutorService() {
        return this.executorService;
    }

}


