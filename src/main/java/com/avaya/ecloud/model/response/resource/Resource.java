package com.avaya.ecloud.model.response.resource;

public class Resource {

    private String name;
    private ServiceReference serviceReference;

    public Resource() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceReference getServiceReference() {
        return serviceReference;
    }

    public void setServiceReference(ServiceReference serviceReference) {
        this.serviceReference = serviceReference;
    }
}
