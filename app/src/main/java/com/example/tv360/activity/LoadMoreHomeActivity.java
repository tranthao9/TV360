package com.example.tv360.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.tv360.R;
import com.example.tv360.adapter.LoadMoreAdapter;
import com.example.tv360.model.DataObjectLoadMore;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMoreHomeActivity extends AppCompatActivity {
    HomeService apiserver ;

    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.loadmorehome);
        loadData();
    }

    private void loadData() {

        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObjectLoadMore> data = apiserver.getCollectionDetail(getIntent().getStringExtra("id"),6,24);
        data.enqueue(new Callback<DataObjectLoadMore>() {
            @Override
            public void onResponse(Call<DataObjectLoadMore> call, Response<DataObjectLoadMore> response) {
                DataObjectLoadMore dataObjectLoadMore = response.body();
                TextView textload = findViewById(R.id.loadmore_home_text);
                textload.setText(dataObjectLoadMore.getData().getName());
                GridView simpleGrid = (GridView) findViewById(R.id.loadmore_home); // init GridView
                // Create an object of CustomAdapter and set Adapter to GirdView
                LoadMoreAdapter customAdapter = new LoadMoreAdapter(getApplicationContext(), dataObjectLoadMore.getData());
                simpleGrid.setAdapter(customAdapter);
                simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {

                        Intent intent = new Intent(LoadMoreHomeActivity.this, PlayingVideoAvtivity.class);
                        String id = dataObjectLoadMore.getData().getContent().get(arg2).getId();
                        String type = dataObjectLoadMore.getData().getContent().get(arg2).getType();
                        intent.putExtra("id",id);
                        intent.putExtra("type",type);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<DataObjectLoadMore> call, Throwable t) {
                call.cancel();
            }
        });

    }


}