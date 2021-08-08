package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Printer extends BaseEntity {
    private long gkey;
    private String id;
    @JsonProperty("host-address")
    private String hostAddress;
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

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    @Override
    public String toString() {
        return "Printer{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", hostAddress='" + hostAddress + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                '}';
    }
}
