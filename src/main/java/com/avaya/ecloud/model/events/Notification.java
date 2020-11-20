package com.avaya.ecloud.model.events;

public class Notification {

    private String application;
    private String resource;
    private String service;
    private Contents contents;
    private String version;

    public Notification() {
    }

    public String getApplication() {
        return application;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
