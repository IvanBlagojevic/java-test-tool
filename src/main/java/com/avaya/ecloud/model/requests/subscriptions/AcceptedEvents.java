package com.avaya.ecloud.model.requests.subscriptions;

import java.util.List;

public class AcceptedEvents {

    private List<String> category;
    private List<String> events;
    private List<String> excludedEvents;

    public AcceptedEvents() {
    }

    public AcceptedEvents(List<String> category, List<String> events, List<String> excludedEvents) {
        this.category = category;
        this.events = events;
        this.excludedEvents = excludedEvents;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public List<String> getExcludedEvents() {
        return excludedEvents;
    }

    public void setExcludedEvents(List<String> excludedEvents) {
        this.excludedEvents = excludedEvents;
    }
}

