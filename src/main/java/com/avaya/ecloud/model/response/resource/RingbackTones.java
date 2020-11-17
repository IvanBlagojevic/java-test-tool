package com.avaya.ecloud.model.response.resource;

public class RingbackTones {

    private Tone fastBusy;
    private Tone intercept;
    private Tone reorder;
    private Tone ringback;

    public RingbackTones() {
    }

    public Tone getFastBusy() {
        return fastBusy;
    }

    public void setFastBusy(Tone fastBusy) {
        this.fastBusy = fastBusy;
    }

    public Tone getIntercept() {
        return intercept;
    }

    public void setIntercept(Tone intercept) {
        this.intercept = intercept;
    }

    public Tone getReorder() {
        return reorder;
    }

    public void setReorder(Tone reorder) {
        this.reorder = reorder;
    }

    public Tone getRingback() {
        return ringback;
    }

    public void setRingback(Tone ringback) {
        this.ringback = ringback;
    }
}
