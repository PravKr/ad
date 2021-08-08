package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportingJobDefinition extends BaseEntity {
    private long gkey;
    @JsonProperty("id")
    private String id;
    @JsonProperty("execution-group-name")
    private String executionGroupName;
    @JsonProperty("report-user-id")
    private String reportUserId;
    @JsonProperty("scope-id")
    private String scopeId;

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

    public String getExecutionGroupName() {
        return executionGroupName;
    }

    public void setExecutionGroupName(String executionGroupName) {
        this.executionGroupName = executionGroupName;
    }

    public String getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    @Override
    public String toString() {
        return "ReportingJobDefinition{" +
                "gkey=" + gkey +
                ", id='" + id + '\'' +
                ", executionGroupName='" + executionGroupName + '\'' +
                ", reportUserId='" + reportUserId + '\'' +
                ", scopeId='" + scopeId + '\'' +
                '}';
    }
}
