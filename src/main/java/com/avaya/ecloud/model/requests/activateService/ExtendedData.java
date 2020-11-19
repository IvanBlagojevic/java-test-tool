package com.avaya.ecloud.model.requests.activateService;

public class ExtendedData {
    private String mode;
    private String mediaServiceType;
    private String recoveryToken;
    private String videoBandwidth;
    private boolean register;
    private boolean subscribeCMEvents;
    private UserAgentData userAgentData;

    public ExtendedData() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMediaServiceType() {
        return mediaServiceType;
    }

    public void setMediaServiceType(String mediaServiceType) {
        this.mediaServiceType = mediaServiceType;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }

    public String getVideoBandwidth() {
        return videoBandwidth;
    }

    public void setVideoBandwidth(String videoBandwidth) {
        this.videoBandwidth = videoBandwidth;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isSubscribeCMEvents() {
        return subscribeCMEvents;
    }

    public void setSubscribeCMEvents(boolean subscribeCMEvents) {
        this.subscribeCMEvents = subscribeCMEvents;
    }

    public UserAgentData getUserAgentData() {
        return userAgentData;
    }

    public void setUserAgentData(UserAgentData userAgentData) {
        this.userAgentData = userAgentData;
    }
}
