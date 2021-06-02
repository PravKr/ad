package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DBMetafield extends BaseEntity {
    private long gkey;
    @JsonProperty("id")
    private String id;
    @JsonProperty("importance")
    private String importance;
    @JsonProperty("short-name")
    private String shortName;

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

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "DBMetafield{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", importance='" + importance + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
