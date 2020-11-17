package com.avaya.ecloud.model.requests.session;

public class Operation {

    private Join join;
    private Stream stream;

    public Operation() {
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }
}
