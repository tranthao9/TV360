package com.example.tv360.ui.TV;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WINDOW_SERVICE;


import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tv360.Interface.TVFragmentInterface;
import com.example.tv360.R;

import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.TVFragmentPresenter;

import com.example.tv360.viewmodel.SharedTVViewModel;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardFragment extends Fragment implements TVFragmentInterface {

    ProgressBar progressBarTV;
    String id;

    SharedPreferences sharedPreferences_tv;
    private static  final  String SHARED_TV_PLAYING = "playingtv";
    private  static  final  String KEY_TV = "id";
   private Boolean isselectedTV = true;

   private View root;

     private   List<HomeModel> datalistTV;

     private TVFragmentPresenter tvFragmentPresenter = new TVFragmentPresenter(this);
    private SharedTVViewModel sharedtvviewmodel ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(isselectedTV)
        {
            root = inflater.inflate(R.layout.fragment_tv, container, false);
            datalistTV = new ArrayList<>();
            progressBarTV = root.findViewById(R.id.progressBar_tv2);
            sharedtvviewmodel = new ViewModelProvider(requireActivity()).get(SharedTVViewModel.class);
            sharedPreferences_tv = getContext().getSharedPreferences(SHARED_TV_PLAYING,MODE_PRIVATE);
            id = sharedPreferences_tv.getString(KEY_TV,"");
            isselectedTV = false;
            return  root;
        }
        return root;
    }

    public  void  updateData(String id)
    {
        sharedtvviewmodel.setSharedData(id,"LIVE",datalistTV);

    }
    @Override
    public void getListHomeLive(List<HomeModel> list) {
        this.datalistTV = list;
        new DownloadDataTaskTV().execute();
    }

    private  class DownloadDataTaskTV extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarTV.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GetData(id);
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
            progressBarTV.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBarTV.setVisibility(View.GONE);
        }
    }

    private void GetData(String id) {

        if(Objects.equals(id, ""))
        {

            sharedtvviewmodel.setSharedData(datalistTV.get(0).getContentPlaying().getDetail().getId(),datalistTV.get(0).getContentPlaying().getDetail().getType(),datalistTV);

        }
        else
        {
            sharedtvviewmodel.setSharedData(id,"LIVE",datalistTV);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if(datalistTV.size() == 0)
        {
            tvFragmentPresenter.getListHomeLive();
        }
    }
}