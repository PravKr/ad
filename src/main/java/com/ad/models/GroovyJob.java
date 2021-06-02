package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroovyJob extends BaseEntity {
    private long gkey;
    @JsonProperty("jobdefId")
    private String jobdefId;
    @JsonProperty("jobdefJobGroup")
    private String jobdefJobGroup;
    @JsonProperty("grvjobCodeType")
    private String grvjobCodeType;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getJobdefId() {
        return jobdefId;
    }

    public void setJobdefId(String jobdefId) {
        this.jobdefId = jobdefId;
    }

    public String getJobdefJobGroup() {
        return jobdefJobGroup;
    }

    public void setJobdefJobGroup(String jobdefJobGroup) {
        this.jobdefJobGroup = jobdefJobGroup;
    }

    public String getGrvjobCodeType() {
        return grvjobCodeType;
    }

    public void setGrvjobCodeType(String grvjobCodeType) {
        this.grvjobCodeType = grvjobCodeType;
    }

    @Override
    public String toString() {
        return "GroovyJob{" +
                "gkey=" + gkey +
                ", jobdefId='" + jobdefId + '\'' +
                ", jobdefJobGroup='" + jobdefJobGroup + '\'' +
                ", grvjobCodeType='" + grvjobCodeType + '\'' +
                '}';
    }
}
