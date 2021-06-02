package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtensionTrigger extends BaseEntity {
    private long gkey;
    @JsonProperty("type")
    private String type;
    @JsonProperty("entity-name")
    private String entityName;
    @JsonProperty("scope")
    private String scope;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "ExtensionTrigger{" +
                "gkey=" + gkey +
                ", type='" + type + '\'' +
                ", entityName='" + entityName + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
