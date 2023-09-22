package com.example.tv360.presenter;

import android.util.Log;

import com.example.tv360.Interface.HomeInterface;
import com.example.tv360.model.DataObjectWatchingAgainTV;
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

    public  InfoPreseter(HomeInterface homeInterface)
    {
        this.mhomeinterface = homeInterface;
    }

    public void getwatchingagain(String id)
    {
        final List<InfoWatchingAgainTV> l = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
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
}
