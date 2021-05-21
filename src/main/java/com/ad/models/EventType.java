package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventType extends BaseEntity{
    private long gkey;
    @JsonProperty("name")
    private String id;
    @JsonProperty("applies-to")
    private String appliesTo;
    @JsonProperty("description")
    private String description;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppliesTo() {
        return appliesTo;
    }

    public void setAppliesTo(String appliesTo) {
        this.appliesTo = appliesTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", appliesTo='" + appliesTo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
