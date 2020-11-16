package com.example.yaml.model.cache;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ResponseDetails {

    private List<String> conferenceIds;
    private List<String> sessionIds;
    private String authToken;
    private String accountId;
    private String accountSecret;
    private String subscriptionId;
    private String sessionToken;
    private String userAgentURL;


    public ResponseDetails() {
    }

    public ResponseDetails(String accountId, String accountSecret, String authToken) {
        this.authToken = authToken;
        this.accountId = accountId;
        this.accountSecret = accountSecret;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserAgentURL() {
        return userAgentURL;
    }

    public void setUserAgentURL(String userAgentURL) {
        this.userAgentURL = userAgentURL;
    }

    public List<String> getConferenceIds() {
        if (CollectionUtils.isEmpty(conferenceIds)) {
            conferenceIds = new ArrayList<>();
        }
        return conferenceIds;
    }

    public void setConferenceIds(List<String> conferenceIds) {
        this.conferenceIds = conferenceIds;
    }

    public List<String> getSessionIds() {
        if (CollectionUtils.isEmpty(sessionIds)) {
            sessionIds = new ArrayList<>();
        }
        return sessionIds;
    }

    public void setSessionIds(List<String> sessionIds) {
        this.sessionIds = sessionIds;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
