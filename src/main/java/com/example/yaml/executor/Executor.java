package com.example.yaml.executor;

import java.util.concurrent.ScheduledExecutorService;

public interface Executor {

    ScheduledExecutorService getExecutorService();
}
