package com.example.yaml.model.command;

import java.util.Map;

public class CommandData {

    private String name;
    private String parent;
    private Map<String, Object> config;

    public String getName() {
        return name;
    }

    public CommandData(String name, String parent, Map<String, Object> config) {
        this.name = name;
        this.parent = parent;
        this.config = config;
    }

    public String getParent() {
        return parent;
    }

    public Map<String, Object> getConfig() {
        return config;
    }
}
