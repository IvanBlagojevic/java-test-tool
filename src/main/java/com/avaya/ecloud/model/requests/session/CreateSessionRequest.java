package com.avaya.ecloud.model.requests.session;

public class CreateSessionRequest {

    private String sessionType;
    private String displayName;
    private String opaqueData;
    private Operation operation;

    public CreateSessionRequest() {
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOpaqueData() {
        return opaqueData;
    }

    public void setOpaqueData(String opaqueData) {
        this.opaqueData = opaqueData;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}




