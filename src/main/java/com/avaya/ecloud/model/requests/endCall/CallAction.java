package com.avaya.ecloud.model.requests.endCall;

import com.avaya.ecloud.model.sdp.ActionDetails;

public class CallAction {

    private String action;
    private ActionDetails actionDetails;

    public CallAction() {
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
