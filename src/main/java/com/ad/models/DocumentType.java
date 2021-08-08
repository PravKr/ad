package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentType extends BaseEntity {
    private long gkey;
    private String id;
    private String usage;
    @JsonProperty("life-cycle-state")
    private String lifeCycleState;
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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DocumentType{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", usage='" + usage + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
