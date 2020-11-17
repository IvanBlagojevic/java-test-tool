package com.avaya.ecloud.model.requests;

public class LoginRequest {

    private String accountId;
    private String accountSecret;

    public LoginRequest(String accountId, String accountSecret) {
        this.accountId = accountId;
        this.accountSecret = accountSecret;
    }

    public LoginRequest() {
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
}
