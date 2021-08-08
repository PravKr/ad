package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateConfiguration extends BaseEntity {
    private long gkey;
    private String name;
    @JsonProperty("life-cycle-state")
    private String lifeCycleState;
    private String description;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "GateConfiguration{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
