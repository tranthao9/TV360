package com.example.tv360.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.example.tv360.Interface.HomeInterface;
import com.example.tv360.R;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.InfoWatchingAgainTV;
import com.example.tv360.model.WatchingAgainTV;
import com.example.tv360.presenter.HomePresenter;
import com.example.tv360.presenter.InfoPreseter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomViewPagerAdapter extends PagerAdapter implements HomeInterface {
    private Context context;
    private List<FilmModel> modelList, Listfimchanel;

    private  List<HomeModel> listcontentfirst;


    private InfoPreseter infoPreseter;

    int positionTV;

    String idchanel;

    WatchingAgainTV watchingAgainTV;

    Map<FilmModel,List<HomeModel>> modelListMap = new HashMap<>();




    public CustomViewPagerAdapter(Context context, List<FilmModel> modelList, List<HomeModel> listcontentfirst, String idchanel) {
        this.context = context;
        this.modelList = modelList;
        this.listcontentfirst = listcontentfirst;
        this.idchanel = idchanel;
        infoPreseter = new InfoPreseter(this);
        infoPreseter.getwatchingagain(idchanel);
        infoPreseter.getflim(listcontentfirst);
       for (int i = 0 ; i<modelList.size(); i++)
       {
           infoPreseter.getlistHomeTablayout(this.context,modelList.get(i));
       }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position == 0)
        {
            View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.main_layour_for_viewpager,container,false);
            RecyclerView recyclerView = view.findViewById(R.id.main_recyclevire_tab);
            MainViewPagerTabLayoutAdapter mainViewPagerTabLayoutAdapter =  new MainViewPagerTabLayoutAdapter(container.getContext(),listcontentfirst,idchanel);
            recyclerView.setAdapter(mainViewPagerTabLayoutAdapter);
            container.addView(view);
            return  view;
        } else if (modelList.get(position).getType().equals("SCHEDULE")) {

            View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.layout_schedule_tv,container,false);
            RecyclerView recyclerView1 = view.findViewById(R.id.recyclerViewChanel);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(),RecyclerView.HORIZONTAL,false);
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.setFocusable(false);//ko focus con tro
            CustomViewPagerScheduleAdapter customViewPagerScheduleAdapter = new CustomViewPagerScheduleAdapter();
            customViewPagerScheduleAdapter.setData(container.getContext(),Listfimchanel,idchanel);
            recyclerView1.setAdapter(customViewPagerScheduleAdapter);
            TextView textView = view.findViewById(R.id.schedule_wathching_gain);
            ImageButton previous = view.findViewById(R.id.previous_calendar);
            ImageButton next = view.findViewById(R.id.next_calendar);
            if(watchingAgainTV != null)
            {
                for (int i = 0; i<watchingAgainTV.getInfo().size();i++)
                {
                    if(watchingAgainTV.getInfo().get(i).getIsSelected() == 1)
                    {
                        positionTV = i;
                        textView.setText(watchingAgainTV.getInfo().get(i).getName());
                    }
                }
                if(watchingAgainTV.getSchedules() != null)
                {
                    RecyclerView recyclerView2 = view.findViewById(R.id.recyclerDetailViewChanel);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(container.getContext(),RecyclerView.VERTICAL,false);
                    recyclerView2.setLayoutManager(linearLayoutManager1);
                    recyclerView2.setFocusable(false);
                    RvScheduleAdapter rvScheduleAdapter = new RvScheduleAdapter(container.getContext(),watchingAgainTV.getSchedules());
                    recyclerView2.setAdapter(rvScheduleAdapter);
                }
            }
            else
            {
                textView.setText("Hôm nay");
            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(positionTV < watchingAgainTV.getInfo().size()-1)
                    {
                        textView.setText(watchingAgainTV.getInfo().get(positionTV + 1).getName());
                        positionTV +=1;
                    }
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(positionTV > 0)
                    {
                        textView.setText(watchingAgainTV.getInfo().get(positionTV - 1).getName());
                        positionTV -= 1;
                    }
                }
            });

            container.addView(view);
            return  view;
        } else
        {

            if(modelListMap != null)
            {
                List<HomeModel> homeModels = new ArrayList<>();
                homeModels = modelListMap.get(modelList.get(position));
                if(homeModels != null)
                {
                    View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.main_layour_for_viewpager,container,false);
                    RecyclerView recyclerView = view.findViewById(R.id.main_recyclevire_tab);
                    MainViewPagerTabLayoutAdapter mainViewPagerTabLayoutAdapter =  new MainViewPagerTabLayoutAdapter(container.getContext(),homeModels,idchanel);
                    recyclerView.setAdapter(mainViewPagerTabLayoutAdapter);
                    container.addView(view);
                    return  view;
                }
                else
                {
                    View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.nondata,container,false);
                    container.addView(view);
                    return  view;
                }
            }
            else
            {
                View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.nondata,container,false);
                container.addView(view);
                return  view;
            }

        }
    }
    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }

    @Override
    public void getwatchingagain(WatchingAgainTV watchingAgainTV) {
        this.watchingAgainTV = watchingAgainTV;
    }

    @Override
    public void getlistHomeTablayout(FilmModel filmModel,List<HomeModel> list) {
        this.modelListMap.put(filmModel,list);
    }

    @Override
    public void getflim(List<FilmModel> listfilm) {
        this.Listfimchanel = listfilm;
        notifyDataSetChanged();
    }
}
