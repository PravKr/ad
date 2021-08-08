package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportableEntity extends BaseEntity {
    private long gkey;
    @JsonProperty("name")
    private String name;
    @JsonProperty("display-name")
    private String displayName;
    @JsonProperty("base-entity")
    private String baseEntity;
    @JsonProperty("description")
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(String baseEntity) {
        this.baseEntity = baseEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReportableEntity{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", baseEntity='" + baseEntity + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
