package com.avaya.ecloud.model.enums;

public enum HttpHeaderEnum {

    LOGIN("application/vnd.avaya.mpaas.authorizationRequest+json", "application/vnd.avaya.mpaas.authorizationinfo+json"),
    CREATE_CONFERENCE("application/vnd.avaya.mpaas.conferencePreferences+json", "application/vnd.avaya.mpaas.conference+json"),
    CREATE_SUBSCRIPTION("application/vnd.avaya.mpaas.subscriptionrequest+json", "application/vnd.avaya.mpaas.subscriptioninfo+json"),
    RESOURCE_DISCOVERY("", "application/vnd.avaya.csa.resources.v1+json "),
    CREATE_SESSION("application/vnd.avaya.mpaas.sessionpreferences+json", "application/vnd.avaya.mpaas.sessioninfo+json"),
    ACTIVATE_SERVICE("application/vnd.avaya.csa.services.v1+json", ""),
    CREATE_NEW_CALL("application/vnd.avaya.csa.callpreferences.v1+json", ""),
    EVENT_SUBSCRIPTION("application/vnd.avaya.mpaas.eventsubscription+json", "application/vnd.avaya.csa.error.v1+json");

    private final String contentType;
    private final String accept;

    HttpHeaderEnum(String contentType, String accept) {
        this.contentType = contentType;
        this.accept = accept;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAccept() {
        return accept;
    }
}
