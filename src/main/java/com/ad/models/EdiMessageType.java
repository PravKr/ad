package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EdiMessageType extends BaseEntity {
    private long gkey;
    @JsonProperty("id")
    private String id;
    @JsonProperty("class")
    private String classs;
    @JsonProperty("standard")
    private String standard;

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

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "EdiMessageType{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", classs='" + classs + '\'' +
                ", standard='" + standard + '\'' +
                '}';
    }
}
