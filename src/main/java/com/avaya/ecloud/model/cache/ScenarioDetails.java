package com.avaya.ecloud.model.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScenarioDetails {
    private String baseUrl;
    private int conferencesCounter;
    private int sessionCounter;
    private int rampTime;
    private int numberOfIterations;
    private String callbackURL;
    private String accountId;
    private String accountSecret;
    private String authToken;
    private List<String> conferenceIds;
    private String subscriptionId;

    public ScenarioDetails() {
    }

    public List<String> getConferenceIds() {
        if (Objects.isNull(conferenceIds)) {
            conferenceIds = new ArrayList<>();
        }
        return conferenceIds;
    }


    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setConferenceIds(List<String> conferenceIds) {
        this.conferenceIds = conferenceIds;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getConferencesCounter() {
        return conferencesCounter;
    }

    public void setConferencesCounter(int conferencesCounter) {
        this.conferencesCounter = conferencesCounter;
    }

    public int getSessionCounter() {
        return sessionCounter;
    }

    public void setSessionCounter(int sessionCounter) {
        this.sessionCounter = sessionCounter;
    }

    public int getRampTime() {
        return rampTime;
    }

    public void setRampTime(int rampTime) {
        this.rampTime = rampTime;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIteration) {
        this.numberOfIterations = numberOfIteration;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountSecret() {
        return accountSecret;
    }

    public void setAccountSecret(String accountSecret) {
        this.accountSecret = accountSecret;
    }
}



