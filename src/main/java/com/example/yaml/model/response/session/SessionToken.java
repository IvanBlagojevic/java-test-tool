package com.example.yaml.model.response;

public class SessionToken {

    private String sessionId;
    private String sessionToken;
    private String userAgentURL;

    public SessionToken() {
    }

    public SessionToken(String sessionId, String sessionToken, String userAgentURL) {
        this.sessionId = sessionId;
        this.sessionToken = sessionToken;
        this.userAgentURL = userAgentURL;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getUserAgentURL() {
        return userAgentURL;
    }
}


