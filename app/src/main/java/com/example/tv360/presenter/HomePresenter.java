package com.example.tv360.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tv360.Interface.HomeInterface;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectWatchingAgainTV;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.InfoWatchingAgainTV;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {



    public HomePresenter() {


    }

    public  List<HomeModel> getdata(List<HomeModel> homeModelList)
    {
        List<HomeModel> listfilm = new ArrayList<>();
        for (HomeModel a : homeModelList)
        {
            listfilm.add(a);

        }
        return  listfilm;
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

    public  List<HomeModel> getlistfilm(List<HomeModel> homeModelList)
    {
        List<HomeModel> listfilm = new ArrayList<>();
        for (HomeModel a : homeModelList)
        {
            if(!a.getType().equals("BANNER") && a.getContent() != null )
            {
                if(a.getContent().size() != 0)
                {
                    listfilm.add(a);
                }
            }

        }
        listfilm.add(homeModelList.get(0));
        return  listfilm;
    }





}
