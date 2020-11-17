package com.avaya.ecloud.executor;

import java.util.concurrent.ScheduledExecutorService;

public interface Executor {

    ScheduledExecutorService getExecutorService();
}
