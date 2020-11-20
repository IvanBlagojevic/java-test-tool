package com.avaya.ecloud.model.events;

import java.util.List;

public class Application {

    private String name;
    private List<Service> services;

    public Application() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
