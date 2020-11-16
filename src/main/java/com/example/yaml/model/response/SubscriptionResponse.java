package com.example.yaml.model.response;

public class SubscriptionResponse {

    private String id;
    private String dateCreated;
    private int expiresIn;

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(String id, String dateCreated, int expiresIn) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.expiresIn = expiresIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
