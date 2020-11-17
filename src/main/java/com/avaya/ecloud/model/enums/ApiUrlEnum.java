package com.avaya.ecloud.model.enums;

public enum ApiUrlEnum {

    LOGIN("v1/mpaas/auth/login/"),
    CREATE_CONFERENCE("v1/mpaas/conferences/"),
    CREATE_SESSION("/v1/mpaas/sessions/"),
    CREATE_SUBSCRIPTION("/v1/mpaas/subscriptions"),
    REFRESH_AUTHORIZATION("v1/mpaas/auth/token");

    private final String value;


    ApiUrlEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
