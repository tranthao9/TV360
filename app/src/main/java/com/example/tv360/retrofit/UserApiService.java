package com.example.tv360.retrofit;

import com.example.tv360.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("public/v1/auth/login")
    @FormUrlEncoded
    Call<UserModel> authorize(
            @Field("grant_type") String grantType,
            @Field("msisdn") String msisdn,
            @Field("username") String username,
            @Field("password") String password,
            @Field("refresh_token") String refreshToken,
            @Field("captcha") String captcha,
            @Field("os_type") String osType,
            @Field("os_version_code") String osVersion);
}
