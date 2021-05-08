package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseVariform extends BaseEntity {
    private long gkey;
    @JsonProperty("cfgvar-name")
    private String name;
    @JsonProperty("cfgvar-description")
    private String description;
    @JsonProperty("cfgvar-enabled")
    private String enabled;
    @JsonProperty("cfgvar-variform-ids")
    private String variformIds;
    @JsonProperty("cfgvar-creator")
    private String creator;
    @JsonProperty("is-checked")
    private Boolean isChecked;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getVariformIds() {
        return variformIds;
    }

    public void setVariformIds(String variformIds) {
        this.variformIds = variformIds;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "DatabaseVariform{" +
                "gkey='" + gkey + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled='" + enabled + '\'' +
                ", variformIds='" + variformIds + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
