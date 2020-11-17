package com.avaya.ecloud.model.requests.conference;

public class Audio {

    private boolean spatialAudio;
    private boolean activeSpeaker;

    public Audio() {
    }

    public Audio(boolean spatialAudio, boolean activeSpeaker) {
        this.spatialAudio = spatialAudio;
        this.activeSpeaker = activeSpeaker;
    }

    public boolean isSpatialAudio() {
        return spatialAudio;
    }

    public void setSpatialAudio(boolean spatialAudio) {
        this.spatialAudio = spatialAudio;
    }

    public boolean isActiveSpeaker() {
        return activeSpeaker;
    }

    public void setActiveSpeaker(boolean activeSpeaker) {
        this.activeSpeaker = activeSpeaker;
    }
}
