package com.avaya.ecloud.model.cache;

public class SessionDetail {

    private String sessionToken;
    private String userAgentURL;
    private String webSocketUri;
    private String sseUri;
    private String terminateClientUri;
    private String deleteSessionUri;
    private String callsUri;
    private String eventsUri;
    private String servicesUri;
    private String callId;

    public SessionDetail(String sessionToken, String userAgentURL) {
        this.sessionToken = sessionToken;
        this.userAgentURL = userAgentURL;
    }

    public SessionDetail() {
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
