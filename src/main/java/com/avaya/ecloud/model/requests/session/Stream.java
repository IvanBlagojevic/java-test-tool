package com.avaya.ecloud.model.requests.session;

public class Stream {

    private AudioStream audio;

    private VideoStream video;

    public Stream() {
    }

    public AudioStream getAudio() {
        return audio;
    }

    public void setAudio(AudioStream audio) {
        this.audio = audio;
    }

    public VideoStream getVideo() {
        return video;
    }

    public void setVideo(VideoStream video) {
        this.video = video;
    }
}
