package com.avaya.ecloud.model.response.startAudioCall;

public class Capability {

    private boolean allowed;
    private String denialReason;

    public Capability() {
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getDenialReason() {
        return denialReason;
    }

    public void setDenialReason(String denialReason) {
        this.denialReason = denialReason;
    }
}
