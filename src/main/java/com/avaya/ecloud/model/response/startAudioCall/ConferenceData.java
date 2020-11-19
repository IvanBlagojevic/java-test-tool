package com.avaya.ecloud.model.response.startAudioCall;

public class ConferenceData {

    private boolean conference;
    private boolean empty;
    private boolean isForceRouting;
    private boolean webRTCableRemote;

    public ConferenceData() {
    }

    public boolean isConference() {
        return conference;
    }

    public void setConference(boolean conference) {
        this.conference = conference;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isForceRouting() {
        return isForceRouting;
    }

    public void setForceRouting(boolean forceRouting) {
        isForceRouting = forceRouting;
    }

    public boolean isWebRTCableRemote() {
        return webRTCableRemote;
    }

    public void setWebRTCableRemote(boolean webRTCableRemote) {
        this.webRTCableRemote = webRTCableRemote;
    }
}
