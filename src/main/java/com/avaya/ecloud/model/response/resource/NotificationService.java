package com.avaya.ecloud.model.response.resource;

public class NotificationService {

    private Notification sse;
    private Notification websocket;

    public NotificationService() {
    }

    public Notification getSse() {
        return sse;
    }

    public void setSse(Notification sse) {
        this.sse = sse;
    }

    public Notification getWebsocket() {
        return websocket;
    }

    public void setWebsocket(Notification websocket) {
        this.websocket = websocket;
    }
}
