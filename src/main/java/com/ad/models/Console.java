package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Console extends BaseEntity {
    private long gkey;
    @JsonProperty("hardware-id")
    private String hardwarId;
    @JsonProperty("hardware-location")
    private String hardwareLocation;
    @JsonProperty("external-console-id")
    private String externalConsoleId;
    @JsonProperty("life-cycle-state")
    private String lifeCycleState;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getHardwarId() {
        return hardwarId;
    }

    public void setHardwarId(String hardwarId) {
        this.hardwarId = hardwarId;
    }

    public String getHardwareLocation() {
        return hardwareLocation;
    }

    public void setHardwareLocation(String hardwareLocation) {
        this.hardwareLocation = hardwareLocation;
    }

    public String getExternalConsoleId() {
        return externalConsoleId;
    }

    public void setExternalConsoleId(String externalConsoleId) {
        this.externalConsoleId = externalConsoleId;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    @Override
    public String toString() {
        return "Console{" +
                "gkey=" + gkey +
                ", hardwarId='" + hardwarId + '\'' +
                ", hardwareLocation='" + hardwareLocation + '\'' +
                ", externalConsoleId='" + externalConsoleId + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                '}';
    }
}
