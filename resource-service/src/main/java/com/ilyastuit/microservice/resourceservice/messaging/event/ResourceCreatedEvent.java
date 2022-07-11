package com.ilyastuit.microservice.resourceservice.messaging.event;

public class ResourceCreatedEvent {
    private long id;

    public ResourceCreatedEvent() {
    }

    public ResourceCreatedEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
