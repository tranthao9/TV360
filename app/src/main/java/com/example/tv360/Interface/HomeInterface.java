package com.example.tv360.Interface;

import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.WatchingAgainTV;

import java.util.List;

public interface HomeInterface {
    void getwatchingagain(WatchingAgainTV watchingAgainTV);

    void  getlistHomeTablayout(FilmModel filmModel,List<HomeModel> list);

    void  getflim(List<FilmModel> listfilm);

}
