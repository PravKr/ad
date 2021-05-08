package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Extension extends BaseEntity {
    private long gkey;
    @JsonProperty("name")
    private String name;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("type")
    private String type;
    @JsonProperty("language")
    private String language;
    @JsonProperty("is-enabled")
    private String isEnabled;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Extension{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", scope='" + scope + '\'' +
                ", type='" + type + '\'' +
                ", language='" + language + '\'' +
                ", isEnabled='" + isEnabled + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
