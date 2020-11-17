package com.avaya.ecloud.model.response.resource;

public class ClientManagement {

    private Client refreshClient;
    private Client terminateClient;
    private Client fastDelete;
    private Client clientRecovery;
    private int timeout;

    public ClientManagement() {
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Client getRefreshClient() {
        return refreshClient;
    }

    public void setRefreshClient(Client refreshClient) {
        this.refreshClient = refreshClient;
    }

    public Client getTerminateClient() {
        return terminateClient;
    }

    public void setTerminateClient(Client terminateClient) {
        this.terminateClient = terminateClient;
    }

    public Client getFastDelete() {
        return fastDelete;
    }

    public void setFastDelete(Client fastDelete) {
        this.fastDelete = fastDelete;
    }

    public Client getClientRecovery() {
        return clientRecovery;
    }

    public void setClientRecovery(Client clientRecovery) {
        this.clientRecovery = clientRecovery;
    }
}
