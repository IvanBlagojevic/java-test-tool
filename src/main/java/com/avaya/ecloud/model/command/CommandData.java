package com.avaya.ecloud.model.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandData {

    private String name;
    private String parent;
    private ResponseData responseData;
    private Map<String, Object> config;

    public CommandData(String name, String parent, ResponseData responseData, Map<String, Object> config) {
        this.name = name;
        this.parent = parent;
        this.responseData = responseData;
        this.config = config;
    }

    public CommandData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommandData() {
    }

    public String getName() {
        return name;
    }

    public CommandData(Map<String, Object> config) {
        setConfig(config);
    }

    public CommandData(String name, Map<String, Object> config) {
        this.name = name;
        setConfig(config);
    }

    public CommandData(String name, String parent, Map<String, Object> config) {
        this.name = name;
        this.parent = parent;
        this.responseData = new ResponseData();
        setConfig(config);
    }

    public CommandData(String name, String parent) {
        this.name = name;
        this.parent = parent;
        this.responseData = new ResponseData();
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public void setConfig(Map<String, Object> config) {
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
        if (Objects.isNull(config)) {
            config = new HashMap<>();
        }
        return config;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


}
