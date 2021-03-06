package com.avaya.ecloud.runner;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.configuration.ScenariosConfig;
import com.avaya.ecloud.executor.Executor;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Runner {

    private Map<String, Command> commandsMap;

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Map<String, Map<CommandData, Command>> clients;

    private Executor executor;

    private Cache cache;

    private ScenariosConfig scenariosConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    @Autowired
    public Runner(@Qualifier("commandsMap") Map<String, Command> commandsMap,
                  Executor executor,
                  Cache cache,
                  ScenariosConfig scenariosConfig) {
        this.commandsMap = commandsMap;
        this.clients = new LinkedHashMap<>();
        this.executor = executor;
        this.cache = cache;
        this.scenariosConfig = scenariosConfig;
    }

    public Executor getExecutor() {
        return executor;
    }


    private void setUp() {
        List<ScenariosConfig.Scenario> scenarioList = scenariosConfig.getScenarios();

        for (ScenariosConfig.Scenario scenario : scenarioList) {
            String scenarioName = scenario.getName();
            cache.put(scenarioName, ModelUtil.getScenarioConfig(scenario));

            List<ScenariosConfig.Task> tasks = scenario.getTasks();

            Map<CommandData, Command> map = new LinkedHashMap<>();
            for (ScenariosConfig.Task task : tasks) {
                String taskName = task.getName();
                map.put(new CommandData(taskName, scenarioName, task.getParams()), commandsMap.get(taskName));
            }
            clients.putIfAbsent(scenarioName, map);
        }
    }

    private void shutdownAndAwaitTermination() {

        ExecutorService executorService = getExecutor().getExecutorService();
        if (Objects.isNull(executorService)) {
            return;
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @PostConstruct
    public void run() throws Exception {
        setUp();
        for (Map.Entry<String, Map<CommandData, Command>> entry : clients.entrySet()) {
            String scenario = entry.getKey();
            logInfo(scenario);
            Map<CommandData, Command> clientsMap = entry.getValue();

            int scenarioCounter = cache.getNumberOfIterations(scenario);
            for (int i = 0; i < scenarioCounter; i++) {
                LOGGER.info("EXECUTING_SCENARIO:" + scenario + "_" + i);
                executorService.execute(() -> createAndExecuteChain(clientsMap));
            }
        }
    }

    //TODO refactor
    private void createAndExecuteChain(Map<CommandData, Command> clientsMap) {
        Command command = null;
        CommandData commandData = null;

        ArrayList<Command> commands = new ArrayList(clientsMap.values());
        ArrayList<CommandData> dataList = new ArrayList(clientsMap.keySet());

        for (int i = commands.size() - 2; i >= 0; i--) {
            command = commands.get(i);
            commandData = dataList.get(i);
            commands.get(i).setNext(commands.get(i + 1));
            commands.get(i).setNextData(dataList.get(i + 1));
        }

        command.execute(commandData);
    }

    private void executeScenario(Map<CommandData, Command> clientsMap) {
        for (Map.Entry<CommandData, Command> clientEntry : clientsMap.entrySet()) {
            clientEntry.getValue().execute(clientEntry.getKey());
        }
    }

    private void logInfo(String name) {
        LOGGER.info("Executing scenario STARTED. Scenario name: " + name);
    }
}
