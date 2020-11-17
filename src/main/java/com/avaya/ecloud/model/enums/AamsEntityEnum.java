package com.avaya.ecloud.model.enums;

public enum AamsEntityEnum {

    CONTROL_CONTEXT("<controlContext version=\"1.0\"/>"),
    CREATE_SESSION("<mediaSessionRequest version=\"1.0\"/>"),
    UPDATE_SESSION("<mediaSessionRequest version=\"1.0\"><sdpAnswer>%s</sdpAnswer></mediaSessionRequest>"),
    INJECT_AUDIO_XML("<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\r\n<msmlRequest><msml version=\"1.1\">\r\n<dialogstart type=\"application/vxml+xml\" src=\"https://lab1.mpaaservice.com/mgmt/pub/dialog/ams-simple-tts\" target=\"conn:%s\">\r\n</dialogstart>\r\n</msml></msmlRequest>"),
    INJECT_AUDIO_TTS("<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\r\n<msmlRequest><msml version=\"1.1\">\r\n<dialogstart name=\"%s\" target=\"conn:%s\" type=\"application/moml+xml\">\r\n<play barge=\"true\" cleardb=\"true\">\r\n<tts>This is a really really long sentence</tts>\r\n<playexit><exit namelist=\"play.amt play.end\" /></playexit>\r\n</play></dialogstart>\r\n</msml></msmlRequest>");

    private final String entity;

    AamsEntityEnum(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }


}
