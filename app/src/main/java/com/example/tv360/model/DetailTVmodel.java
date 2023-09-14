package com.example.tv360.model;

import java.util.List;

public class DetailTVmodel {
    FilmModel detail;

    public DetailTVmodel(FilmModel detail) {
        this.detail = detail;
    }

    public FilmModel getDetail() {
        return detail;
    }

    public void setDetail(FilmModel detail) {
        this.detail = detail;
    }
}
