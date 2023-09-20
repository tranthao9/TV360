package com.example.tv360.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tv360.Interface.HomeInterface;
import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.adapter.MainViewPagerTabLayoutAdapter;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectWatchingAgainTV;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.InfoWatchingAgainTV;
import com.example.tv360.model.WatchingAgainTV;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

//    private HomeInterface mhomeinterface;

    private HomeService apiserver;

    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";
    public HomePresenter() {

    }

    public  List<HomeModel> getdata(List<HomeModel> homeModelList)
    {
        List<HomeModel> listfilm = new ArrayList<>();
        for (HomeModel a : homeModelList)
        {
            if(a.getType().equals("COLLECTION") || a.getType().equals("BANNER"))
            {
                listfilm.add(a);
            }

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
            if(a.getType().equals("COLLECTION") && a.getContent() != null )
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

    public  List<HomeModel> getlistHomeTablayout(Context context,FilmModel filmModel)
    {
        final List<HomeModel> list = new ArrayList<>();
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObject> data  = apiserver.getCategoryLive(filmModel.getId(),6,0);
        data.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {
                DataObject data = response.body();
                for (HomeModel l : data.getData())
                {
                    list.add(l);
                }

            }
            @Override
            public void onFailure(Call<DataObject> call, Throwable t) {

            }
        });
        return  list;
    }

    public  List<FilmModel> getflim(@NonNull List<HomeModel> model)
    {
        List<FilmModel> list = new ArrayList<>();
        for (HomeModel h : model)
        {
            for (FilmModel f : h.getContent())
            {
                list.add(f);
            }

        }
        return list;
    }

    public List<InfoWatchingAgainTV> getwatchingagain(String id)
    {
        final  List<InfoWatchingAgainTV> l = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        apiserver = ApiService.getClient().create(HomeService.class);
        Log.d("Datetime "+date,"time");
        Call<DataObjectWatchingAgainTV> data  = apiserver.getLiveSchedule(id, date);
        data.enqueue(new Callback<DataObjectWatchingAgainTV>() {
            @Override
            public void onResponse(Call<DataObjectWatchingAgainTV> call, Response<DataObjectWatchingAgainTV> response) {
                DataObjectWatchingAgainTV data = response.body();
                WatchingAgainTV watchingAgainTV1 = data.getData();
                for (InfoWatchingAgainTV i : watchingAgainTV1.getInfo())
                {
                    Log.d("InfoM ","info "+l);
                    l.add(i);
                }
            }
            @Override
            public void onFailure(Call<DataObjectWatchingAgainTV> call, Throwable t) {
                call.cancel();
            }
        });
        return l;
    }
}
