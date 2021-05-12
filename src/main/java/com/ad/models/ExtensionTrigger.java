package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtensionTrigger extends BaseEntity {
    private long gkey;
    @JsonProperty("name")
    private String level;
    @JsonProperty("scope")
    private String extensionType;
    @JsonProperty("type")
    private String extension;
    @JsonProperty("type")
    private boolean isEnabled;
    private String entityName;

    public long getGkey() {
        return gkey;
    }

    public void setGkey(long gkey) {
        this.gkey = gkey;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(String extensionType) {
        this.extensionType = extensionType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "ExtensionTrigger{" +
                "gkey=" + gkey +
                ", level='" + level + '\'' +
                ", extensionType='" + extensionType + '\'' +
                ", extension='" + extension + '\'' +
                ", isEnabled=" + isEnabled +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
