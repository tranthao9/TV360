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

public class InfoPreseter {

    private HomeInterface mhomeinterface;

    private HomeService apiserver;


    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    public  InfoPreseter(HomeInterface homeInterface)
    {
        this.mhomeinterface = homeInterface;
    }

    public void getwatchingagain(String id, String date)
    {

        apiserver = ApiService.getClient().create(HomeService.class);
        Log.d("Datetime "+date,"time");
        Call<DataObjectWatchingAgainTV> data  = apiserver.getLiveSchedule(id, date);
        data.enqueue(new Callback<DataObjectWatchingAgainTV>() {
            @Override
            public void onResponse(Call<DataObjectWatchingAgainTV> call, Response<DataObjectWatchingAgainTV> response) {
                DataObjectWatchingAgainTV data = response.body();
                Log.d("data after get ", "got data : "+data.getData());
                mhomeinterface.getwatchingagain(data.getData());
            }
            @Override
            public void onFailure(Call<DataObjectWatchingAgainTV> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public  void getlistHomeTablayout(Context context, FilmModel filmModel)
    {
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
                for (HomeModel h : data.getData())
                {
                    if(h.getContent() != null && h.getContent().size() > 0)
                    {
                        mhomeinterface.getlistHomeTablayout(filmModel,data.getData());
                        break;
                    }
                }
            }
            @Override
            public void onFailure(Call<DataObject> call, Throwable t) {
            }
        });
    }

    public  void getflim(@NonNull List<HomeModel> model)
    {
        List<FilmModel> list = new ArrayList<>();
        for (HomeModel h : model)
        {
            for (FilmModel f : h.getContent())
            {
                list.add(f);
            }

        }
        mhomeinterface.getflim(list);
    }
}
