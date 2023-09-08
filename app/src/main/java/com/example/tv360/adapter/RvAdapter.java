package com.example.tv360.adapter;

import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.tv360.MainActivity;
import com.example.tv360.R;
import com.example.tv360.model.FilmImageModel;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.ImageModel;
import com.example.tv360.model.ItemModel;
import com.example.tv360.model.ListFilmModel;
import com.example.tv360.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class RvAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private List<HomeModel> listitems;

    Runnable runnable;
    Handler handler;


    private List<HomeModel> item;

    private List<FilmModel> banner;

    private LinearLayout layout_dots;
    private ViewPager viewPager;

    VPAdapter adapterImageSlider;

    ListFilmAdapter listFilmAdapter;

    private RecyclerView rcvData;
    public RvAdapter(Context context,List<HomeModel> item) {
        this.context = context;
        this.item = item;
        this.banner = new HomePresenter().getbanner(this.item);
    }
    public static class  RowHolder extends  RecyclerView.ViewHolder{
        public  RowHolder (@NonNull View itemView)
        {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
//            final  int currentItem = viewPager.getCurrentItem();
            startAutoSlider(adapterImageSlider.getCount());


            return  new RowHolder(view);

        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_rcv,parent,false);
            listFilmAdapter = new ListFilmAdapter();
            listFilmAdapter.setData(parent.getContext(),getListData());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(parent.getContext());
            rcvData = (RecyclerView) view.findViewById(R.id.rcv_film);
            rcvData.setLayoutManager(linearLayoutManager);

            listFilmAdapter.setData(parent.getContext(),getListData());
            rcvData.setAdapter(listFilmAdapter);
            return  new RowHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e(getClass().getSimpleName(), "onBindViewHolder: " + holder);
    }

    @Override
    public  int getItemViewType (int position)
    {
            if(position > 0)
            {
                return  1;
            }
        return  position % 2;
    }

    @Override
    public int getItemCount() {
        return this.item.size();
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

    private List<ListFilmModel> getListData() {
        List<ListFilmModel> listData = new ArrayList<>();

        List<FilmModel> listModel = new ArrayList<>();
//        listModel.add(new FilmModel("Kênh TV"));


        List<FilmImageModel> listCategory = new ArrayList<>();
        listCategory.add(new FilmImageModel(R.drawable.a1));
        listCategory.add(new FilmImageModel(R.drawable.a2));
        listCategory.add(new FilmImageModel(R.drawable.a3));
        listCategory.add(new FilmImageModel(R.drawable.a4));
        listCategory.add(new FilmImageModel(R.drawable.a5));
        listCategory.add(new FilmImageModel(R.drawable.facebook));


        listData.add(new ListFilmModel(ListFilmAdapter.TYPE_MODEL,listModel,null));
        listData.add(new ListFilmModel(ListFilmAdapter.TYPE_CATEGORY,null,listCategory));
        listData.add(new ListFilmModel(ListFilmAdapter.TYPE_MODEL,listModel,null));
        listData.add(new ListFilmModel(ListFilmAdapter.TYPE_CATEGORY,null,listCategory));
        listData.add(new ListFilmModel(ListFilmAdapter.TYPE_MODEL,listModel,null));
        listData.add(new ListFilmModel(ListFilmAdapter.TYPE_CATEGORY,null,listCategory));

        return listData;
    }

}