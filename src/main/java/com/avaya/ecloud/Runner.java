package com.avaya.ecloud;

import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.configuration.ScenariosConfig;
import com.avaya.ecloud.model.requests.conference.CreateConferenceRequest;
import com.avaya.ecloud.model.cache.ScenarioDetails;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.executor.Executor;
import com.avaya.ecloud.model.command.CommandData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Runner {

    private Map<String, Command> commandsMap;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Map<String, Map<Command, CommandData>> clients;

    private Executor executor;

    private ScenarioCache cache;

    private ScenariosConfig scenariosConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    @Autowired
    public Runner(@Qualifier("commandsMap") Map<String, Command> commandsMap, Executor executor, ScenarioCache cache, ScenariosConfig scenariosConfig) {
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
            cache.put(scenarioName, getScenarioConfig(scenario));

            List<ScenariosConfig.Task> tasks = scenario.getTasks();

            Map<Command, CommandData> map = new LinkedHashMap<>();
            for (ScenariosConfig.Task task : tasks) {
                map.put(commandsMap.get(task.getName()), new CommandData(task.getName(), scenarioName, task.getParams()));
            }
            clients.putIfAbsent(scenarioName, map);
        }
    }

    private ScenarioDetails getScenarioConfig(ScenariosConfig.Scenario scenario) {
        try {
            InputStream is = CreateConferenceRequest.class.getClassLoader().getResourceAsStream((String) scenario.getParams().get("config"));
            return OBJECT_MAPPER.readValue(is, ScenarioDetails.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
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
        for (Map.Entry<String, Map<Command, CommandData>> entry : clients.entrySet()) {
            String scenario = entry.getKey();
            logInfo(scenario);
            Map<Command, CommandData> clientsMap = entry.getValue();


            getExecutor().getExecutorService().execute(() -> executeScenario(clientsMap));
        }
    }

    private void executeScenario(Map<Command, CommandData> clientsMap) {
        for (Map.Entry<Command, CommandData> clientEntry : clientsMap.entrySet()) {
            clientEntry.getKey().execute(clientEntry.getValue());
        }
    }


//    @PostConstruct
//    public void run() throws Exception {
//        setScenarios();
//        setBaseUrl();
//        Map<String, List<Client>> clients = getClients();
//        for (Map.Entry<String, List<Client>> entry : clients.entrySet()) {
//            String scenario = entry.getKey();
//            logInfo(scenario);
//            getExecutor().getExecutorService().execute(() -> entry.getValue().forEach(e -> e.execute(scenario)));
//        }
//    }

    private void logInfo(String name) {
        LOGGER.info("Executing scenario STARTED. Scenario name: " + name);
    }
}
