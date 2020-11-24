package com.avaya.ecloud.model.sdp;

import com.avaya.ecloud.model.requests.startAudioCall.Channel;
import com.avaya.ecloud.model.requests.startAudioCall.Participant;

import java.util.List;

public class ActionDetails {

    private List<Participant> participants;
    private Channel audioChannel;
    private int videoBandwidth;
    private boolean forceIceRestart;
    private String sdp;
    private String sdpType;
    private boolean remote;
    private boolean isPrivate;

    public ActionDetails() {
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Channel getAudioChannel() {
        return audioChannel;
    }

    public void setAudioChannel(Channel audioChannel) {
        this.audioChannel = audioChannel;
    }

    public int getVideoBandwidth() {
        return videoBandwidth;
    }

    public void setVideoBandwidth(int videoBandwidth) {
        this.videoBandwidth = videoBandwidth;
    }

    public boolean isForceIceRestart() {
        return forceIceRestart;
    }

    public void setForceIceRestart(boolean forceIceRestart) {
        this.forceIceRestart = forceIceRestart;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

    public String getSdpType() {
        return sdpType;
    }

    public void setSdpType(String sdpType) {
        this.sdpType = sdpType;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
