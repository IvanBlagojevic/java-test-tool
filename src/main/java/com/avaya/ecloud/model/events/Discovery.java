package com.avaya.ecloud.model.events;

import java.util.List;

public class Discovery {

    private SessionManagement sessionManagement;
    private List<String> capabilities;
    private List<Application> applications;
    private String version;

    public Discovery() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SessionManagement getSessionManagement() {
        return sessionManagement;
    }

    public void setSessionManagement(SessionManagement sessionManagement) {
        this.sessionManagement = sessionManagement;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
