package com.avaya.ecloud.aams;

import com.avaya.ecloud.model.aams.SessionInfo;

public interface AamsConnection {

    void createControlContext();

    SessionInfo createSession();

    void updateSession(String sessionId, String sdpAnswer);

    void deleteSession(String sessionId);

    void injectAudioViaXml(String sessionId);

    void injectAudioViaTts(String sessionId);





}
