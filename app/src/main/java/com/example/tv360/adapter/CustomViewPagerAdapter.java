package com.example.tv360.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.tv360.R;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.InfoWatchingAgainTV;
import com.example.tv360.model.WatchingAgainTV;
import com.example.tv360.presenter.HomePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class CustomViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<FilmModel> modelList;

    private  List<HomeModel> listcontentfirst;

    private HomePresenter homePresenter;

    TabLayout tabLayout;

    ViewPager viewPager;

    String idchanel;



    public CustomViewPagerAdapter(Context context, List<FilmModel> modelList, List<HomeModel> listcontentfirst, String idchanel) {
        this.context = context;
        this.modelList = modelList;
        this.listcontentfirst = listcontentfirst;
        this.idchanel = idchanel;
        homePresenter = new HomePresenter();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position == 0)
        {
            View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.main_layour_for_viewpager,container,false);
            RecyclerView recyclerView = view.findViewById(R.id.main_recyclevire_tab);
            MainViewPagerTabLayoutAdapter mainViewPagerTabLayoutAdapter =  new MainViewPagerTabLayoutAdapter(container.getContext(),listcontentfirst);
            recyclerView.setAdapter(mainViewPagerTabLayoutAdapter);
            container.addView(view);
            return  view;
        } else if (modelList.get(position-1).getType().equals("SCHEDULE")) {
            View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.layout_schedule_tv,container,false);
            RecyclerView recyclerView1 = view.findViewById(R.id.recyclerViewChanel);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(),RecyclerView.HORIZONTAL,false);
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.setFocusable(false);//ko focus con tro
            CustomViewPagerScheduleAdapter customViewPagerScheduleAdapter = new CustomViewPagerScheduleAdapter();
            customViewPagerScheduleAdapter.setData(container.getContext(),homePresenter.getflim(listcontentfirst));
            recyclerView1.setAdapter(customViewPagerScheduleAdapter);
            TextView textView = view.findViewById(R.id.schedule_wathching_gain);
            List<InfoWatchingAgainTV> watchingAgainTV = homePresenter.getwatchingagain(idchanel);
            Log.d("watchingAgainTV chanel ","ok" + watchingAgainTV);
            Log.d("Id chanel "+idchanel,"ok");
            if(watchingAgainTV.size() != 0)
            {
                textView.setText(watchingAgainTV.get(0).getName());
            }
            else
            {
                textView.setText("Data rỗng");
            }
            container.addView(view);
            return  view;
        } else
        {
            List<HomeModel> homeModelList = homePresenter.getlistHomeTablayout(container.getContext(),modelList.get(position-1));
            View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.main_layour_for_viewpager,container,false);
            RecyclerView recyclerView = view.findViewById(R.id.main_recyclevire_tab);
            MainViewPagerTabLayoutAdapter mainViewPagerTabLayoutAdapter =  new MainViewPagerTabLayoutAdapter(container.getContext(),homeModelList);
            recyclerView.setAdapter(mainViewPagerTabLayoutAdapter);
            container.addView(view);
            return  view;
        }
    }
    @Override
    public int getCount() {
        return modelList.size() + 1;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
