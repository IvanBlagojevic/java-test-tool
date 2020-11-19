package com.avaya.ecloud.model.requests.activateService;

public class ActivateService {

    private String action;
    private String service;
    private ExtendedData extendedData;

    public ActivateService() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ExtendedData getExtendedData() {
        return extendedData;
    }

    public void setExtendedData(ExtendedData extendedData) {
        this.extendedData = extendedData;
    }
}
