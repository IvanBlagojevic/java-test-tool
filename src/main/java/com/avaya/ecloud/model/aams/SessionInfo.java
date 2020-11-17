package com.avaya.ecloud.model.aams;

public class SessionInfo {

    private String sid;
    private String sdpOffer;


    //TODO refactor
    public SessionInfo(String sid, String sdpOffer) {
        this.sid = sid;
        this.sdpOffer = sdpOffer.replaceAll("\r\n", "\\\\\\\\r\\\\\\\\n");
    }

    public String getSid() {
        return sid;
    }

    public String getSdpOffer() {
        return sdpOffer;
    }

}
