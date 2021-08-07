package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StorageRule extends BaseEntity {
    private long gkey;
    @JsonProperty("rule-id")
    private String ruleId;
    @JsonProperty("start-day-rule")
    private String startDayRule;
    @JsonProperty("end-day-rule")
    private String endDayRule;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getStartDayRule() {
        return startDayRule;
    }

    public void setStartDayRule(String startDayRule) {
        this.startDayRule = startDayRule;
    }

    public String getEndDayRule() {
        return endDayRule;
    }

    public void setEndDayRule(String endDayRule) {
        this.endDayRule = endDayRule;
    }

    @Override
    public String toString() {
        return "StorageRule{" +
                "gkey=" + gkey +
                ", ruleId='" + ruleId + '\'' +
                ", startDayRule='" + startDayRule + '\'' +
                ", endDayRule='" + endDayRule + '\'' +
                '}';
    }
}
