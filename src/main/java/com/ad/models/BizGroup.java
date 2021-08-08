package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BizGroup extends BaseEntity {
    private long gkey;
    private String id;
    private String name;
    @JsonProperty("life-cycle-state")
    private String lifeCycleState;

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

    @Override
    public String toString() {
        return "BizGroup{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                '}';
    }
}
