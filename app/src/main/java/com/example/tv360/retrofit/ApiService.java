package com.example.tv360.retrofit;

import android.content.SharedPreferences;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.example.tv360.model.HomeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();


    private static Retrofit retrofit = null;

    private static Retrofit retrofit2 = null;


    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();
                        builder.addHeader("authorization", "bearer kadfkasfksf");
                        builder.addHeader("Content-Type", "application/json");
                        builder.addHeader("lang","vi");
                        builder.addHeader("zoneid","1");
                        builder.addHeader("osapptype","WAP");
                        builder.addHeader("osappversion","3.4");
                        builder.addHeader("devicetype","WAP");
                        Request request = builder.method(original.method(), original.body()).build();
                        return chain.proceed(request);
                    }
                }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://local-a.tivi360.vn:30900/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }

    public static Retrofit getlink(String profileid,String userId,String deviceid,String authorization) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();
                        builder.addHeader("lang","vi");
                        builder.addHeader("zoneid","1");
                        builder.addHeader("osapptype","ANDROIDBOX");
                        builder.addHeader("osappversion","1.9.5");
                        builder.addHeader("devicetype","ANDROIDBOX");
                        builder.addHeader("profileid",profileid);
                        builder.addHeader("userid",userId);
                        builder.addHeader("deviceid",deviceid);
                        builder.addHeader("Authorization",authorization);
                        Request request = builder.method(original.method(), original.body()).build();
                        return chain.proceed(request);
                    }
                }).build();


        retrofit2 = new Retrofit.Builder()
                .baseUrl("http://local-a.tivi360.vn:30900/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit2;

    }
}

