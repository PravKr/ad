package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralReference extends BaseEntity {
    private long gkey;
    private String refType;
    private String refId1;
    private String refValue1;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRefId1() {
        return refId1;
    }

    public void setRefId1(String refId1) {
        this.refId1 = refId1;
    }

    public String getRefValue1() {
        return refValue1;
    }

    public void setRefValue1(String refValue1) {
        this.refValue1 = refValue1;
    }

    @Override
    public String toString() {
        return "GeneralReference{" +
                "gkey=" + gkey +
                ", refType='" + refType + '\'' +
                ", refId1='" + refId1 + '\'' +
                ", refValue1='" + refValue1 +
                '}';
    }
}
