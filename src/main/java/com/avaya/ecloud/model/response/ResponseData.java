package com.avaya.ecloud.model.response;

public class ResponseData {

    private String authToken;
    private String subscriptionId;
    private String sessionToken;
    private String sessionId;
    private String userAgentURL;
    private String webSocketUri;
    private String sseUri;
    private String terminateClientUri;
    private String deleteSessionUri;
    private String callsUri;
    private String eventsUri;
    private String servicesUri;
    private String callId;
    private String requestBody;

    public ResponseData() {
    }

    public ResponseData(String sessionToken, String sessionId, String userAgentURL, String accessToken) {
        this.sessionToken = sessionToken;
        this.sessionId = sessionId;
        this.userAgentURL = userAgentURL;
        this.authToken = accessToken;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserAgentURL() {
        return userAgentURL;
    }

    public String getWebSocketUri() {
        return webSocketUri;
    }

    public void setWebSocketUri(String webSocketUri) {
        this.webSocketUri = webSocketUri;
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

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}
