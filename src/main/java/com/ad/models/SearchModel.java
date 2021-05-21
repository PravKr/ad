package com.ad.models;

public class SearchModel {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SearchModel{" +
                "text='" + text + '\'' +
                '}';
    }
}
