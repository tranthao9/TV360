package com.example.tv360.presenter;

import android.util.Log;

import com.example.tv360.Interface.TVFragmentInterface;
import com.example.tv360.model.DataObject;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVFragmentPresenter {

    private TVFragmentInterface tvFragmentInterface;

    private  HomeService apiInterface;

    public TVFragmentPresenter(TVFragmentInterface tvFragmentInterface) {
        this.tvFragmentInterface = tvFragmentInterface;
    }

    public  void  getListHomeLive()
    {
        apiInterface = ApiService.getClient().create(HomeService.class);
        Call<DataObject> data = apiInterface.getTVBox();
        data.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {
                DataObject dataObject = response.body();
                Log.d("data day ", " .... "+dataObject.getData());
                tvFragmentInterface.getListHomeLive(dataObject.getData());
            }
            @Override
            public void onFailure(Call<DataObject> call, Throwable t) {
                call.cancel();
            }
        });

    }
}
