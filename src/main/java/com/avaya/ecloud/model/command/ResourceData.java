package com.avaya.ecloud.model.command;

import com.avaya.ecloud.model.response.resource.Resource;

import java.util.List;

public class ResourceData {

    private String webSocketUri;
    private String sseUri;
    private String terminateClientUri;
    private String deleteSessionUri;
    private String callsUri;
    private String eventsUri;
    private String servicesUri;

    public ResourceData(String webSocketUri, String sseUri, String terminateClientUri, String deleteSessionUri, List<Resource> resources) {
        this.webSocketUri = webSocketUri;
        this.sseUri = sseUri;
        this.terminateClientUri = terminateClientUri;
        this.deleteSessionUri = deleteSessionUri;
        setResourcesUri(resources);
    }

    private void setResourcesUri(List<Resource> resources) {
        for (Resource resource : resources) {
            String name = resource.getName();
            String href = resource.getServiceReference().getHref();

            switch (name) {
                case "calls":
                    this.callsUri = href;
                    break;
                case "mpaasevents":
                    this.eventsUri = href;
                    break;
                case "services":
                    this.servicesUri = href;
                    break;
            }
        }
    }

    public String getWebSocketUri() {
        return webSocketUri;
    }

    public String getSseUri() {
        return sseUri;
    }

    public String getTerminateClientUri() {
        return terminateClientUri;
    }

    public String getDeleteSessionUri() {
        return deleteSessionUri;
    }

    public String getCallsUri() {
        return callsUri;
    }

    public String getEventsUri() {
        return eventsUri;
    }

    public String getServicesUri() {
        return servicesUri;
    }
}
