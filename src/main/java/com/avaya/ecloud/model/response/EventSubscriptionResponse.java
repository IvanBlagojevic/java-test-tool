package com.avaya.ecloud.model.response;

public class EventSubscriptionResponse {

    private SubscribedEvents subscribedEvents;

    public EventSubscriptionResponse() {
    }

    public SubscribedEvents getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(SubscribedEvents subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }
}
