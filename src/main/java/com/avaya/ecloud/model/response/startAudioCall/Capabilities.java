package com.avaya.ecloud.model.response.startAudioCall;

public class Capabilities {

    private Capability holdCapability;
    private Capability sendDtmfCapability;
    private Capability unholdCapability;
    private Capability updateVideoChannelsCapability;
    private Capability updatecallpropertiesCapability;

    public Capabilities() {
    }

    public Capability getHoldCapability() {
        return holdCapability;
    }

    public void setHoldCapability(Capability holdCapability) {
        this.holdCapability = holdCapability;
    }

    public Capability getSendDtmfCapability() {
        return sendDtmfCapability;
    }

    public void setSendDtmfCapability(Capability sendDtmfCapability) {
        this.sendDtmfCapability = sendDtmfCapability;
    }

    public Capability getUnholdCapability() {
        return unholdCapability;
    }

    public void setUnholdCapability(Capability unholdCapability) {
        this.unholdCapability = unholdCapability;
    }

    public Capability getUpdateVideoChannelsCapability() {
        return updateVideoChannelsCapability;
    }

    public void setUpdateVideoChannelsCapability(Capability updateVideoChannelsCapability) {
        this.updateVideoChannelsCapability = updateVideoChannelsCapability;
    }

    public Capability getUpdatecallpropertiesCapability() {
        return updatecallpropertiesCapability;
    }

    public void setUpdatecallpropertiesCapability(Capability updatecallpropertiesCapability) {
        this.updatecallpropertiesCapability = updatecallpropertiesCapability;
    }
}
