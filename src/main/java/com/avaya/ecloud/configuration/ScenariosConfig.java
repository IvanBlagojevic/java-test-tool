package com.avaya.ecloud.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ConstructorBinding
@ConfigurationProperties("mpaas")
public class ScenariosConfig {

    private final List<Scenario> scenarios;

    public ScenariosConfig(ArrayList<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public static class Scenario {
        private final String name;

        private final List<Task> tasks;

        private final Map<String, Object> params;

        public Scenario(String name, List<Task> tasks, Map<String, Object> params) {
            this.name = name;
            this.tasks = tasks;
            this.params = params;
        }

        public String getName() {
            return name;
        }

        public List<Task> getTasks() {
            return tasks;
        }

        public Map<String, Object> getParams() {
            return params;
        }
    }

    public static class Task {

        private final String name;
        private final Map<String, Object> params;

        public Task(String name, Map<String, Object> params) {
            this.name = name;
            this.params = params;
        }

        public String getName() {
            return name;
        }

        public Map<String, Object> getParams() {
            return params;
        }
    }
}
