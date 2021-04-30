package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DigitalAsset extends BaseEntity{
    private long gkey;
    @JsonProperty("name")
    private String id;
    @JsonProperty("short-description")
    private String shortDescription;
    @JsonProperty("is-pre-deployed")
    private String isPreDeployed;
    @JsonProperty("format")
    private String format;

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getIsPreDeployed() {
        return isPreDeployed;
    }

    public void setIsPreDeployed(String isPreDeployed) {
        this.isPreDeployed = isPreDeployed;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "DigitalAsset{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", isPreDeployed='" + isPreDeployed + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
