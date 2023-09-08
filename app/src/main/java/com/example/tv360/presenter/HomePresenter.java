package com.example.tv360.presenter;

import com.example.tv360.Interface.HomeInterface;
import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter {

//    private HomeInterface mhomeinterface;
    public HomePresenter() {

    }
    public List<FilmModel> getbanner(List<HomeModel> homeModelList)
    {
        List<FilmModel> listbanner = new ArrayList<>();
        for (HomeModel a : homeModelList)
        {
            if(a.getType().equals("BANNER"))
            {
                for (FilmModel l : a.getContent())
                {
                    listbanner.add(l);
                }

            }

        }
        return  listbanner;
    }
}
