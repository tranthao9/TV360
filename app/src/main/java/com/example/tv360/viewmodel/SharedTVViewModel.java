package com.example.tv360.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tv360.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class SharedTVViewModel extends ViewModel {
    private MutableLiveData<List<String>> sharedData =  new MutableLiveData<>();

    private MutableLiveData<List<HomeModel>> sharedDataHome =  new MutableLiveData<List<HomeModel>>();

    public  void  setSharedData (String data, String type, List<HomeModel> listhomemodel)
    {
        List<String> list = new ArrayList<>();
        list.add(data);
        list.add(type);
        sharedDataHome.postValue(listhomemodel);
        sharedData.postValue(list);
    }

    public LiveData<List<String>> getSharedData(){
        return sharedData;
    }

    public  LiveData<List<HomeModel>> getSharedDataHome(){ return sharedDataHome; }

}
