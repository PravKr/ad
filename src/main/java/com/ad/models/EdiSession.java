package com.ad.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EdiSession extends BaseEntity {
    private long gkey;
    @JsonProperty("name")
    private String name;
    @JsonProperty("message-class")
    private String messageClass;
    @JsonProperty("message-map")
    private String messageMap;

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

    public String getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(String messageClass) {
        this.messageClass = messageClass;
    }

    public String getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(String messageMap) {
        this.messageMap = messageMap;
    }

    @Override
    public String toString() {
        return "EdiSession{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", messageClass='" + messageClass + '\'' +
                ", messageMap='" + messageMap + '\'' +
                '}';
    }
}
