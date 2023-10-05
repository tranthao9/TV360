package com.example.tv360.model;

import java.util.List;

public class DataSearchSuggest {
    private  String message;

    private List<FilmModel> data;

    public DataSearchSuggest(String message, List<FilmModel> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FilmModel> getData() {
        return data;
    }

    public void setData(List<FilmModel> data) {
        this.data = data;
    }
}
