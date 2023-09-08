package com.example.tv360.model;

import java.util.List;

public class ListFilmModel {
    public  int type;

    public   List<FilmModel> lisfilm;
   public List<FilmImageModel> listfilmimage;


    public ListFilmModel(int type, List<FilmModel> lisfilm, List<FilmImageModel> listfilmimage) {
        this.type = type;
        this.lisfilm = lisfilm;
        this.listfilmimage = listfilmimage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<FilmModel> getLisfilm() {
        return lisfilm;
    }

    public void setLisfilm(List<FilmModel> lisfilm) {
        this.lisfilm = lisfilm;
    }

    public List<FilmImageModel> getListfilmimage() {
        return listfilmimage;
    }

    public void setListfilmimage(List<FilmImageModel> listfilmimage) {
        this.listfilmimage = listfilmimage;
    }
}
