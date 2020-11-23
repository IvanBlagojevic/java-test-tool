package com.avaya.ecloud.model.requests.subscriptions;

public class SubscriptionRequest {

    private String callbackURL;
    private AcceptedEvents acceptedEvents;

    public SubscriptionRequest() {
    }

    public SubscriptionRequest(String callbackURL, AcceptedEvents acceptedEvents) {
        this.callbackURL = callbackURL;
        this.acceptedEvents = acceptedEvents;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public AcceptedEvents getAcceptedEvents() {
        return acceptedEvents;
    }

    public void setAcceptedEvents(AcceptedEvents acceptedEvents) {
        this.acceptedEvents = acceptedEvents;
    }
}



