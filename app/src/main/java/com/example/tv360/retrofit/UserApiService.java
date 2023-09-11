package com.example.tv360.retrofit;

import com.example.tv360.model.DataObjectLogin;
import com.example.tv360.model.DeviceInfo;
import com.example.tv360.model.LoginModel;
import com.example.tv360.model.UserModel;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("public/v1/auth/login")
    Call<DataObjectLogin> login(@Body LoginModel body);

}
