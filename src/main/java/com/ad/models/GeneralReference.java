package com.ad.models;

public class GeneralReference extends BaseEntity {
    private long gkey;
    private String refType;
    private String refId1;
    private String refValue1;
    private String refCreator;
    private String refCreated;

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

    public String getRefCreator() {
        return refCreator;
    }

    public void setRefCreator(String refCreator) {
        this.refCreator = refCreator;
    }

    public String getRefCreated() {
        return refCreated;
    }

    public void setRefCreated(String refCreated) {
        this.refCreated = refCreated;
    }

    @Override
    public String toString() {
        return "GeneralReference{" +
                "gkey=" + gkey +
                ", refType='" + refType + '\'' +
                ", refId1='" + refId1 + '\'' +
                ", refValue1='" + refValue1 + '\'' +
                ", refCreator='" + refCreator + '\'' +
                ", refCreated='" + refCreated + '\'' +
                '}';
    }
}
