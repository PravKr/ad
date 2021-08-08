package com.ad.models;

public class ArgoSecRole extends BaseEntity {
    private long gkey;
    private String name;
    private String operator;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ArgoSecRole{" +
                "gkey=" + gkey +
                ", name='" + name + '\'' +
                ", operator='" + operator + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
