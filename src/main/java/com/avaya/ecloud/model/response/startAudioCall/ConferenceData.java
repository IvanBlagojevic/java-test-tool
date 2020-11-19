package com.avaya.ecloud.model.response.startAudioCall;

public class ConferenceData {

    private boolean conference;
    private boolean empty;
    private boolean webRTCAbleRemote;
    private boolean isForceRouting;

    public ConferenceData() {
    }

    public boolean isConference() {
        return conference;
    }

    public boolean getIsForceRouting() {
        return isForceRouting;
    }

    public void setIsForceRouting(boolean isForceRouting) {
        this.isForceRouting = isForceRouting;
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

    public boolean isWebRTCAbleRemote() {
        return webRTCAbleRemote;
    }

    public void setWebRTCAbleRemote(boolean webRTCableRemote) {
        this.webRTCAbleRemote = webRTCableRemote;
    }
}
