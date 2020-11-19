package com.avaya.ecloud.model.response.startAudioCall;

import java.util.List;

public class UpdatePropertyCapability {

    private boolean allowed;
    private List<String> requestTypes;

    public UpdatePropertyCapability() {
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public List<String> getRequestTypes() {
        return requestTypes;
    }

    public void setRequestTypes(List<String> requestTypes) {
        this.requestTypes = requestTypes;
    }
}



