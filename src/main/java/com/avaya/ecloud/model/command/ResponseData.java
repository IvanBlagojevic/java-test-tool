package com.avaya.ecloud.model.command;

public class ResponseData {

    private String authToken;
    private String subscriptionId;
    private String sessionToken;
    private String sessionId;
    private String userAgentURL;
    private String callId;
    private String requestBody;
    private ResourceData resourceData;

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

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public ResourceData getResourceData() {
        return resourceData;
    }

    public void setResourceData(ResourceData resourceData) {
        this.resourceData = resourceData;
    }
}
