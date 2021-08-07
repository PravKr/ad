package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HoldPermissionView extends BaseEntity {
    private long gkey;
    @JsonProperty("id")
    private String id;
    @JsonProperty("applies-to")
    private String appliesTo;
    @JsonProperty("type")
    private String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HoldPermissionView{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", appliesTo='" + appliesTo + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
