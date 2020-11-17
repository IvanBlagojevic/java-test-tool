package com.avaya.ecloud.model.requests.conference;

public class Video {

    private String maxResolution;
    private String mixerMode;
    private int maxCompositeWindows;
    private int escalateOnBanwdith;
    private int escalateOnSessions;
    private int maxFramerate;
    private int maxBitrate;
    private boolean allowDeescalation;
    private boolean nameOverlay;

    public Video() {
    }

    public String getMaxResolution() {
        return maxResolution;
    }

    public void setMaxResolution(String maxResolution) {
        this.maxResolution = maxResolution;
    }

    public String getMixerMode() {
        return mixerMode;
    }

    public void setMixerMode(String mixerMode) {
        this.mixerMode = mixerMode;
    }

    public int getMaxCompositeWindows() {
        return maxCompositeWindows;
    }

    public void setMaxCompositeWindows(int maxCompositeWindows) {
        this.maxCompositeWindows = maxCompositeWindows;
    }

    public int getEscalateOnBanwdith() {
        return escalateOnBanwdith;
    }

    public void setEscalateOnBanwdith(int escalateOnBanwdith) {
        this.escalateOnBanwdith = escalateOnBanwdith;
    }

    public int getEscalateOnSessions() {
        return escalateOnSessions;
    }

    public void setEscalateOnSessions(int escalateOnSessions) {
        this.escalateOnSessions = escalateOnSessions;
    }

    public int getMaxFramerate() {
        return maxFramerate;
    }

    public void setMaxFramerate(int maxFramerate) {
        this.maxFramerate = maxFramerate;
    }

    public int getMaxBitrate() {
        return maxBitrate;
    }

    public void setMaxBitrate(int maxBitrate) {
        this.maxBitrate = maxBitrate;
    }

    public boolean isAllowDeescalation() {
        return allowDeescalation;
    }

    public void setAllowDeescalation(boolean allowDeescalation) {
        this.allowDeescalation = allowDeescalation;
    }

    public boolean isNameOverlay() {
        return nameOverlay;
    }

    public void setNameOverlay(boolean nameOverlay) {
        this.nameOverlay = nameOverlay;
    }
}
