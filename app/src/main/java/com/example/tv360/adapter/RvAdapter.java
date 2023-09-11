package com.example.tv360.adapter;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.tv360.R;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class RvAdapter extends  RecyclerView.Adapter<RvAdapter.RowHolder> {

    Context context;
    private List<HomeModel> listitems;

    Runnable runnable;
    Handler handler;

    private RecyclerView rcvData;
    private List<HomeModel> item;

    private List<FilmModel> banner;

    private List<HomeModel> listflim;

    private  List<FilmModel> films;

    private LinearLayout layout_dots;
    private ViewPager viewPager;

    VPAdapter adapterImageSlider;

    ListFilmAdapter listFilmAdapter;

    HomePresenter homePresenter;

    public RvAdapter(Context context,List<HomeModel> item) {
        this.homePresenter = new HomePresenter();
        this.context = context;
        this.item = item;
        this.banner = homePresenter.getbanner(this.item);
        this.listflim = homePresenter.getlistfilm(this.item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.e(getClass().getSimpleName(), "onCreateViewHolder: " + viewType);
        if (viewType == 0)
        {
            handler = new Handler();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_slider,parent,false);
            layout_dots = (LinearLayout) view.findViewById(R.id.layout_dots);
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            adapterImageSlider = new VPAdapter (parent.getContext(), new ArrayList<FilmModel>());
            adapterImageSlider.setItems(banner);
            viewPager.setAdapter(adapterImageSlider);
            // displaying selected image first
            viewPager.setCurrentItem(0);
            addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int pos) {
                    addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            startAutoSlider(adapterImageSlider.getCount());
            return  new RowHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_rcv,parent,false);
            return  new RowHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        HomeModel listData = listflim.get(position);
        if (listflim.get(position) == null){
            return;
        }
        if(holder.rcvData != null)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
            holder.rcvData.setLayoutManager(linearLayoutManager);
            holder.rcvData.setFocusable(false);//ko focus con tro
            ListFilmAdapter modelAdapter = new ListFilmAdapter();
            modelAdapter.setData(listflim.get(position-1));
            holder.rcvData.setAdapter(modelAdapter);
        }


    }

    @Override
    public  int getItemViewType (int position)
    {
        return  position ;
    }

    @Override
    public int getItemCount() {
        return listflim.size();
    }


    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(adapterImageSlider.context);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 0, 10, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.circle_outline);
            dots[i].setColorFilter(ContextCompat.getColor(adapterImageSlider.context, R.color.grey40), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.circle_shape);
            dots[current].setColorFilter(ContextCompat.getColor(adapterImageSlider.context, R.color.grey40), PorterDuff.Mode.SRC_ATOP);
        }
    }


        public class RowHolder extends RecyclerView.ViewHolder {

        private RecyclerView  rcvData;
            public RowHolder(@NonNull View itemView  ) {
                super(itemView);
                rcvData =  itemView.findViewById(R.id.rcv_film);

            }
        }
}