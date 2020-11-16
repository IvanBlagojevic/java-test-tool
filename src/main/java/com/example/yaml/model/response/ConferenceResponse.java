package com.example.yaml.model.response;

public class ConferenceResponse  {

    private String id;
    private String dateCreated;
    private String lastUpdated;
    private int sessionCount;

    public ConferenceResponse() {
    }

    public ConferenceResponse(String id, String dateCreated, String lastUpdated, int sessionCount) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.sessionCount = sessionCount;
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

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }
}
