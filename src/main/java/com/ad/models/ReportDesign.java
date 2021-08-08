package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportDesign extends BaseEntity {
    private long gkey;
    @JsonProperty("name")
    private String name;
    @JsonProperty("entity")
    private String entity;
    @JsonProperty("visible-scope")
    private String visibleScope;
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

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getVisibleScope() {
        return visibleScope;
    }

    public void setVisibleScope(String visibleScope) {
        this.visibleScope = visibleScope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReportDesign{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", entity='" + entity + '\'' +
                ", visibleScope='" + visibleScope + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
