package com.avaya.ecloud.model.response.resource;

import java.util.List;

public class ResourceDiscoveryResponse {

    private List<String> capabilities;
    private String clientId;
    private ClientManagement clientManagement;
    private NotificationService notificationService;
    private List<Resource> resources;
    private RingbackTones ringbackTones;
    private List<String> stunServers;
    private SessionManagement sessionManagement;

    public ResourceDiscoveryResponse() {
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientManagement getClientManagement() {
        return clientManagement;
    }

    public void setClientManagement(ClientManagement clientManagement) {
        this.clientManagement = clientManagement;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public RingbackTones getRingbackTones() {
        return ringbackTones;
    }

    public void setRingbackTones(RingbackTones ringbackTones) {
        this.ringbackTones = ringbackTones;
    }

    public List<String> getStunServers() {
        return stunServers;
    }

    public void setStunServers(List<String> stunServers) {
        this.stunServers = stunServers;
    }

    public SessionManagement getSessionManagement() {
        return sessionManagement;
    }

    public void setSessionManagement(SessionManagement sessionManagement) {
        this.sessionManagement = sessionManagement;
    }
}


