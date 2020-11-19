package com.avaya.ecloud.model.response.startAudioCall;

public class Capabilities {

    private Capability holdCapability;
    private Capability sendDtmfCapability;
    private Capability unholdCapability;
    private Capability updateVideoChanelsCapability;
    private UpdatePropertyCapability updatecallpropertiesCapability;

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

    public Capability getUpdateVideoChanelsCapability() {
        return updateVideoChanelsCapability;
    }

    public void setUpdateVideoChanelsCapability(Capability updateVideoChanelsCapability) {
        this.updateVideoChanelsCapability = updateVideoChanelsCapability;
    }

    public UpdatePropertyCapability getUpdatecallpropertiesCapability() {
        return updatecallpropertiesCapability;
    }

    public void setUpdatecallpropertiesCapability(UpdatePropertyCapability updatecallpropertiesCapability) {
        this.updatecallpropertiesCapability = updatecallpropertiesCapability;
    }
}
