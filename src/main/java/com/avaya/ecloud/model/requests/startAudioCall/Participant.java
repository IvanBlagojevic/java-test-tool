package com.avaya.ecloud.model.requests.startAudioCall;

public class Participant {

    private String participantId;
    private String remoteDisplayName;
    private String remoteAddress;

    public Participant() {
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getRemoteDisplayName() {
        return remoteDisplayName;
    }

    public void setRemoteDisplayName(String remoteDisplayName) {
        this.remoteDisplayName = remoteDisplayName;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}


