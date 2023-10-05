package com.example.tv360.model;

import java.util.List;

public class SearchContentModel {
    private  String id;
    private  String name;
    private  String type;
    private List<String> content;

    public SearchContentModel(String id, String name, String type, List<String> content) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
