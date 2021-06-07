package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralNotice  extends BaseEntity{
    private long gkey;
    @JsonProperty("event-type")
    private String eventType;
    @JsonProperty("action")
    private String action;
    @JsonProperty("business-entity")
    private String businessEntity;
    @JsonProperty("description")
    private String description;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GeneralNotice{" +
                "gkey=" + gkey +
                ", eventType='" + eventType + '\'' +
                ", action='" + action + '\'' +
                ", businessEntity='" + businessEntity + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}