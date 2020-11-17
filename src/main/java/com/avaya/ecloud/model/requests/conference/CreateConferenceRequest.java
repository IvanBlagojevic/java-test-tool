package com.avaya.ecloud.model.requests.conference;

public class CreateConferenceRequest {

    private boolean persistent;
    private Long id;
    private String roomName;
    private String description;
    private boolean enableAudio;
    private boolean enableVideo;
    private boolean enableWebCollaboration;
    private Audio audio;
    private Video video;
    private Controls controls;

    public CreateConferenceRequest() {
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnableAudio() {
        return enableAudio;
    }

    public void setEnableAudio(boolean enableAudio) {
        this.enableAudio = enableAudio;
    }

    public boolean isEnableVideo() {
        return enableVideo;
    }

    public void setEnableVideo(boolean enableVideo) {
        this.enableVideo = enableVideo;
    }

    public boolean isEnableWebCollaboration() {
        return enableWebCollaboration;
    }

    public void setEnableWebCollaboration(boolean enableWebCollaboration) {
        this.enableWebCollaboration = enableWebCollaboration;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }
}



