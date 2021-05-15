package com.ad.models;

public class Argo extends BaseEntity {
    private String id;
    private String systemType;
    private String systemName;
    private String endPoint;
    private String username;
    private String password;
    private String operator;
    private String complex;
    private String facility;
    private String yard;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getComplex() {
        return complex;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getYard() {
        return yard;
    }

    public void setYard(String yard) {
        this.yard = yard;
    }

    @Override
    public String toString() {
        return "Argo{" +
                "id='" + id + '\'' +
                ", systemType='" + systemType + '\'' +
                ", systemName='" + systemName + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", operator='" + operator + '\'' +
                ", complex='" + complex + '\'' +
                ", facility='" + facility + '\'' +
                ", yard='" + yard + '\'' +
                '}';
    }
}
