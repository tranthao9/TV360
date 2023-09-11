package com.example.tv360.retrofit;

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

public class ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

//    ApiService apiService = new Retrofit.Builder()
//            .baseUrl("https://tv360.vn/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(ApiService.class);


    private static Retrofit retrofit = null;

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
                        String userAgent = System.getProperty("http.agent");
                        Request.Builder builder = original.newBuilder();
                        builder.addHeader("authorization", "bearer kadfkasfksf");
                        builder.addHeader("Content-Type", "application/json");
                        builder.addHeader("lang","vi");
                        builder.addHeader("zoneid","1");
                        builder.addHeader("osapptype","WAP");



                        Request request = builder.method(original.method(), original.body()).build();
                        return chain.proceed(request);
                    }
                }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tv360.vn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }
}

//baseUrl("https://api.tv360.vn/")
