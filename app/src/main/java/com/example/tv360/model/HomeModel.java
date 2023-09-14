package com.example.tv360.model;

import java.security.PublicKey;
import java.util.List;

public class HomeModel {
    private  String id;
    private  String name;
    private  String type;

    private int display;

    private  String description;
    private List<FilmModel> content;

    private  DetailTVmodel contentPlaying;

    public HomeModel(String id, String name, String type, int display, String description, List<FilmModel> content, DetailTVmodel contentPlaying) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.display = display;
        this.description = description;
        this.content = content;
        this.contentPlaying = contentPlaying;
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

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FilmModel> getContent() {
        return content;
    }

    public void setContent(List<FilmModel> content) {
        this.content = content;
    }

    public DetailTVmodel getContentPlaying() {
        return contentPlaying;
    }

    public void setContentPlaying(DetailTVmodel contentPlaying) {
        this.contentPlaying = contentPlaying;
    }
}
