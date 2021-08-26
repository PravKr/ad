package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EdiTradingPartner extends BaseEntity {
    private long gkey;
    private String name;
    @JsonProperty("business-id")
    private String businessId;
    @JsonProperty("business-role")
    private String businessRole;
    @JsonProperty("life-cycle-state")
    private String lifeCycleState;

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

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessRole() {
        return businessRole;
    }

    public void setBusinessRole(String businessRole) {
        this.businessRole = businessRole;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    @Override
    public String toString() {
        return "EdiTradingPartner{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", businessId='" + businessId + '\'' +
                ", businessRole='" + businessRole + '\'' +
                ", lifeCycleState='" + lifeCycleState + '\'' +
                '}';
    }
}
