package com.example.tv360.retrofit;

import com.example.tv360.model.DataObject;
import com.example.tv360.model.HomeModel;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface HomeService {

    @GET("public/v1/composite/get-home")
    Call<DataObject> getHomeBox();

    @GET("public/v1/composite/get-link")
    Call<JsonElement> getlinka(
                              @Query("id") String id,
                              @Query("type") String type);
}
