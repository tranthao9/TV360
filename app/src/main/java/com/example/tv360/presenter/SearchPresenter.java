package com.example.tv360.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.example.tv360.Interface.SearchInterface;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectSearchContent;
import com.example.tv360.model.KeywordHistory;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    private SearchInterface searchInterface;
    private HomeService apiserver;
    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";


    public SearchPresenter() {
    }

    public  void  removeHistory(String key, Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObjectSearchContent> data  = apiserver.searchRemoveHistory(new KeywordHistory(key));
        data.enqueue(new Callback<DataObjectSearchContent>() {
            @Override
            public void onResponse(Call<DataObjectSearchContent> call, Response<DataObjectSearchContent> response) {
                DataObjectSearchContent objectSearchContent = response.body();
                searchInterface.removeHistory(objectSearchContent.getData().get(0));
            }

            @Override
            public void onFailure(Call<DataObjectSearchContent> call, Throwable t) {

            }
        });
    }
}
