package com.avaya.ecloud.model.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        setConfig(config);
    }

    private void setConfig(Map<String, Object> config) {
        if (!Objects.isNull(config)) {
            this.config = new HashMap<>();
            for (Map.Entry<String, Object> entry : config.entrySet()) {
                this.config.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public String getParent() {
        return parent;
    }

    public Map<String, Object> getConfig() {
        return config;
    }
}
