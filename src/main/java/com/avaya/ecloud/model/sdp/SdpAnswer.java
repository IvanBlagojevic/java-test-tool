package com.avaya.ecloud.model.sdp;

public class SdpAnswer {

    private String action;
    private ActionDetails actionDetails;

    public SdpAnswer() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ActionDetails getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(ActionDetails actionDetails) {
        this.actionDetails = actionDetails;
    }
}



