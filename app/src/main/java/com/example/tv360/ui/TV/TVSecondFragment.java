package com.example.tv360.ui.TV;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.tv360.R;
import com.example.tv360.adapter.CustomViewPagerAdapter;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.viewmodel.SharedTVViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TVSecondFragment extends Fragment {

    private  View view ;

    private TabLayout tabLayout ;
    private ViewPager viewPager;

    private  CustomViewPagerAdapter customViewPagerAdapter;

    private Boolean isScroll = false;

    private SharedTVViewModel sharedTVViewModel;

    List<FilmModel> listData = new ArrayList<>();

    List<HomeModel> listcontentFirst = new ArrayList<>();

    SharedPreferences sharedPreferences_tv;
    private static  final  String SHARED_TV_PLAYING = "playingtv";
    private  static  final  String KEY_TV = "id";

    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_t_v_second, container, false);
        tabLayout  = (TabLayout) view.findViewById(R.id.tabLayout_main);
        viewPager =(ViewPager) view.findViewById(R.id.viewlayout_pager);
        sharedPreferences_tv = getContext().getSharedPreferences(SHARED_TV_PLAYING,MODE_PRIVATE);
        id = sharedPreferences_tv.getString(KEY_TV,"");
        sharedTVViewModel = new ViewModelProvider(requireActivity()).get(SharedTVViewModel.class);
        sharedTVViewModel.getSharedDataHome().observe(getViewLifecycleOwner(), new Observer<List<HomeModel>>() {
            @Override
            public void onChanged(List<HomeModel> list) {
                setData(list);
            }
        });

        return view;
    }

    private void setData(List<HomeModel> datalistTV)
    {
        listData = datalistTV.get(1).getContent();
        for (int i =2 ; i < datalistTV.size() ; i++)
        {
            if (datalistTV.get(i).getContent() != null)
            {
                listcontentFirst.add(datalistTV.get(i));
            }
        }
        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run() {
                tabLayout.addTab(tabLayout.newTab().setText("Tất cả"));
                for (int i =1 ; i<listData.size();i++)
                {
                    tabLayout.addTab(tabLayout.newTab().setText(listData.get(i).getName()));
                }
                for (int i = 0;i<tabLayout.getTabCount();i++)
                {
                    View view  = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    params.setMargins(20,0,30,20);
                    view.requestLayout();
                }
                if(Objects.equals(id, ""))
                {
                    customViewPagerAdapter = new CustomViewPagerAdapter(getContext(),listData,listcontentFirst,datalistTV.get(0).getContentPlaying().getDetail().getId());
                    viewPager.setAdapter(customViewPagerAdapter);
                    viewPager.setCurrentItem(0);
                }
                else
                {
                    customViewPagerAdapter = new CustomViewPagerAdapter(getContext(),listData,listcontentFirst,id);
                    viewPager.setAdapter(customViewPagerAdapter);
                    viewPager.setCurrentItem(0);
                }

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.selectTab(tabLayout.getTabAt(tab.getPosition()));
                if(isScroll)
                {
                    viewPager.setCurrentItem(tab.getPosition(),true);
                    isScroll = false;
                    return;
                }
                else
                {
                    viewPager.setCurrentItem(tab.getPosition(),false);
                    isScroll = false;
                    return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                isScroll = true;
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageSelected(int position) {
                isScroll = false;
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}