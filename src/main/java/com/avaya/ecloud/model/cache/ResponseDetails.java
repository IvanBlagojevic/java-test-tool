package com.avaya.ecloud.model.cache;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseDetails {

    private List<String> conferenceIds;
    private String authToken;
    private String accountId;
    private String accountSecret;
    private String subscriptionId;
    private Map<String, SessionDetail> sessionDetail;

    public ResponseDetails() {
        this.sessionDetail = new HashMap<>();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountSecret() {
        return accountSecret;
    }

    public void setAccountSecret(String accountSecret) {
        this.accountSecret = accountSecret;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Map<String, SessionDetail> getSessionDetail() {
        return sessionDetail;
    }

    public void setSessionDetail(Map<String, SessionDetail> sessionDetail) {
        this.sessionDetail = sessionDetail;
    }

    public List<String> getConferenceIds() {
        if (CollectionUtils.isEmpty(conferenceIds)) {
            conferenceIds = new ArrayList<>();
        }
        return conferenceIds;
    }

    public void setConferenceIds(List<String> conferenceIds) {
        this.conferenceIds = conferenceIds;
    }


}
