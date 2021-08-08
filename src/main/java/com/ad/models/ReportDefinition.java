package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportDefinition extends BaseEntity {
    private long gkey;
    @JsonProperty("name")
    private String name;
    @JsonProperty("report-design")
    private String reportDesign;
    @JsonProperty("report-type")
    private String reportType;
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

    public String getReportDesign() {
        return reportDesign;
    }

    public void setReportDesign(String reportDesign) {
        this.reportDesign = reportDesign;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReportDefinition{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", reportDesign='" + reportDesign + '\'' +
                ", reportType='" + reportType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
