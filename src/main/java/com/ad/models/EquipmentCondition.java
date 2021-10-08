package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquipmentCondition  extends BaseEntity {
    private long gkey;
    @JsonProperty("id")
    private String id;
    @JsonProperty("classs")
    private String classs;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is-persist")
    private String isPersist;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsPersist() {
        return isPersist;
    }

    public void setIsPersist(String isPersist) {
        this.isPersist = isPersist;
    }

    @Override
    public String toString() {
        return "EquipmentCondition{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", classs='" + classs + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
