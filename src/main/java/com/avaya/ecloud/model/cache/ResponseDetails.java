package com.avaya.ecloud.model.cache;

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
    private String websocketUri;
    private String sseUri;
    private String terminateClientUri;
    private String deleteSessionUri;
    private String callsUri;
    private String eventsUri;
    private String servicesUri;
    private String callId;


    public ResponseDetails() {
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public ResponseDetails(String accountId, String accountSecret, String authToken) {
        this.authToken = authToken;
        this.accountId = accountId;
        this.accountSecret = accountSecret;
    }

    public String getCallsUri() {
        return callsUri;
    }

    public void setCallsUri(String callsUri) {
        this.callsUri = callsUri;
    }

    public String getEventsUri() {
        return eventsUri;
    }

    public void setEventsUri(String eventsUri) {
        this.eventsUri = eventsUri;
    }

    public String getServicesUri() {
        return servicesUri;
    }

    public void setServicesUri(String servicesUri) {
        this.servicesUri = servicesUri;
    }

    public String getWebsocketUri() {
        return websocketUri;
    }

    public void setWebsocketUri(String websocketUri) {
        this.websocketUri = websocketUri;
    }

    public String getSseUri() {
        return sseUri;
    }

    public void setSseUri(String sseUri) {
        this.sseUri = sseUri;
    }

    public String getTerminateClientUri() {
        return terminateClientUri;
    }

    public void setTerminateClientUri(String terminateClientUri) {
        this.terminateClientUri = terminateClientUri;
    }

    public String getDeleteSessionUri() {
        return deleteSessionUri;
    }

    public void setDeleteSessionUri(String deleteSessionUri) {
        this.deleteSessionUri = deleteSessionUri;
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
