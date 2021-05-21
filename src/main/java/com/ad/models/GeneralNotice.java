package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralNotice  extends BaseEntity{
    private long gkey;
    @JsonProperty("action")
    private String action;
    @JsonProperty("business-entity")
    private String businessEntity;
    @JsonProperty("event-type")
    private String eventType;

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

    @Override
    public String toString() {
        return "GeneralNotice{" +
                "gkey=" + gkey +
                ", action='" + action + '\'' +
                ", businessEntity='" + businessEntity + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}