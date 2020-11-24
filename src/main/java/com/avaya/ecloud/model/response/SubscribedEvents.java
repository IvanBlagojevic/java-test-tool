package com.avaya.ecloud.model.response;

import java.util.List;

public class SubscribedEvents {

    private List<String> events;

    public SubscribedEvents() {
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
