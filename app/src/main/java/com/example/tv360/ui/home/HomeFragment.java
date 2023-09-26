package com.example.tv360.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

//import androidx.fragment.app.Fragment;
import android.support.v4.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tv360.R;
import com.example.tv360.adapter.RvAdapter;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.HomePresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RvAdapter rvAdapter;
    List<HomeModel> listitem = new ArrayList<HomeModel>();
    HomeService apiInterface;

    private ProgressBar progressBar;
    private boolean isselected  = true;

    RecyclerView viewhome;

    private  View viewfragmenthome;

    HomePresenter homePresenter = new HomePresenter();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (isselected)
        {
            viewfragmenthome = inflater.inflate(R.layout.fragment_home,container,false);
            progressBar = viewfragmenthome.findViewById(R.id.progressBar_tv);
            viewhome = viewfragmenthome.findViewById(R.id.viewhome);
            viewhome.setLayoutManager(new LinearLayoutManager(getActivity()));
            isselected = false;
            return viewfragmenthome;
        }
        else
        {
            return viewfragmenthome;
        }
    }

    private  class DownloadDataTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GetData();
            for (int i = 0 ; i<100; i+=10)
            {
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void GetData(){

        apiInterface = ApiService.getClient().create(HomeService.class);
        Call<DataObject> data = apiInterface.getHomeBox();

        data.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {

                DataObject dataObject = response.body();

                listitem = homePresenter.getdata(dataObject.getData());

                rvAdapter=new RvAdapter(getActivity(), listitem);
                viewhome.setAdapter(rvAdapter);
            }
            @Override
            public void onFailure(Call<DataObject> call, Throwable throwable) {
                call.cancel();
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listitem.size() == 0)
        {
            new DownloadDataTask().execute();
        }
    }
}