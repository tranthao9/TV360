package com.example.tv360.model;

import java.security.PublicKey;
import java.util.List;

public class HomeModel {
    private  String id;
    private  String name;
    private  String type;

    private String  itemType;

    private int  orderKey;

    private int display;
    private  String slug;
    private  String description;
    private List<FilmModel> content;

    public HomeModel(String id, String name, String type, List<FilmModel> content) {
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


    public List<FilmModel> getContent() {
        return content;
    }

    public void setContent(List<FilmModel> content) {
        this.content = content;
    }
}
