package com.avaya.ecloud.model.requests.subscriptions;

public class EventSubscriptionRequest {

    private String sessionId;
    private AcceptedEvents acceptedEvents;

    public EventSubscriptionRequest() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public AcceptedEvents getAcceptedEvents() {
        return acceptedEvents;
    }

    public void setAcceptedEvents(AcceptedEvents acceptedEvents) {
        this.acceptedEvents = acceptedEvents;
    }
}
