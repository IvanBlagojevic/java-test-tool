package com.avaya.ecloud.model.response.startAudioCall;


import com.avaya.ecloud.model.requests.startAudioCall.Participant;

import java.util.List;

public class StartAudioCallResponse {

    private String callId;
    private Channel audioChannel;
    private Capabilities capabilities;
    private int clientCpuUsageMgmt;
    private ConferenceData conferenceData;
    private long creationTime;
    private String href;
    private int videoBandwidth;
    private List<Channel> videoChannels;
    private List<Participant> participants;


    public StartAudioCallResponse() {
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Channel getAudioChannel() {
        return audioChannel;
    }

    public void setAudioChannel(Channel audioChannel) {
        this.audioChannel = audioChannel;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public int getClientCpuUsageMgmt() {
        return clientCpuUsageMgmt;
    }

    public void setClientCpuUsageMgmt(int clientCpuUsageMgmt) {
        this.clientCpuUsageMgmt = clientCpuUsageMgmt;
    }

    public ConferenceData getConferenceData() {
        return conferenceData;
    }

    public void setConferenceData(ConferenceData conferenceData) {
        this.conferenceData = conferenceData;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getVideoBandwidth() {
        return videoBandwidth;
    }

    public void setVideoBandwidth(int videoBandwidth) {
        this.videoBandwidth = videoBandwidth;
    }

    public List<Channel> getVideoChannels() {
        return videoChannels;
    }

    public void setVideoChannels(List<Channel> videoChannels) {
        this.videoChannels = videoChannels;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
