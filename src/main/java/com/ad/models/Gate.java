package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Gate extends BaseEntity {
    private long gkey;
    private String id;
    @JsonProperty("gate-config-id")
    private String gateConfigId;
    @JsonProperty("facility-id")
    private String facilityId;
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

    public String getGateConfigId() {
        return gateConfigId;
    }

    public void setGateConfigId(String gateConfigId) {
        this.gateConfigId = gateConfigId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", gateConfigId='" + gateConfigId + '\'' +
                ", facilityId='" + facilityId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
