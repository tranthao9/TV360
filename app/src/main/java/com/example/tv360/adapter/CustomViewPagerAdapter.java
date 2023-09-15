package com.example.tv360.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tv360.R;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectUrlVideo;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.HomePresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<FilmModel> modelList;

    private HomePresenter homePresenter = new HomePresenter();



    public CustomViewPagerAdapter(Context context, List<FilmModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_layour_for_viewpager,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.main_recyclevire_tab);
        List<HomeModel> homeModelList = homePresenter.getlistHomeTablayout(context,modelList.get(position));
        MainViewPagerTabLayoutAdapter mainViewPagerTabLayoutAdapter =  new MainViewPagerTabLayoutAdapter(context,homeModelList);
        mainViewPagerTabLayoutAdapter.setData(homeModelList);
        recyclerView.setAdapter(mainViewPagerTabLayoutAdapter);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }


}
