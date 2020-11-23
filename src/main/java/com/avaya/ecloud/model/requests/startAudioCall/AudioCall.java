package com.avaya.ecloud.model.requests.startAudioCall;

import java.util.List;

public class AudioCall {

    private String sessionId;
    private String subject;
    private String priority;
    private String privacy;
    private List<Participant> participants;
    private Channel audioChannel;
    private List<Channel> videoChannels;
    private String desiredBandwidth;

    public List<Channel> getVideoChannels() {
        return videoChannels;
    }

    public void setVideoChannels(List<Channel> videoChannels) {
        this.videoChannels = videoChannels;
    }

    public AudioCall() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public String getDesiredBandwidth() {
        return desiredBandwidth;
    }

    public void setDesiredBandwidth(String desiredBandwidth) {
        this.desiredBandwidth = desiredBandwidth;
    }

    public Channel getAudioChannel() {
        return audioChannel;
    }

    public void setAudioChannel(Channel audioChannel) {
        this.audioChannel = audioChannel;
    }

}




