package com.example.tv360.adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tv360.R;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.HomePresenter;

import java.util.List;

public class MainViewPagerTabLayoutAdapter extends RecyclerView.Adapter<MainViewPagerTabLayoutAdapter.MainViewPagerTabLayoutViewHolder> {

    private Context context;
    private List<HomeModel> listhomemodels;

    String id;

    public MainViewPagerTabLayoutAdapter(Context context, List<HomeModel> listhomemodels,String id) {
        this.context = context;
        this.listhomemodels = listhomemodels;
        this.id = id;
        notifyDataSetChanged();
    }

    public void  setData(String id)
    {
        this.id = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewPagerTabLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tablayout_viewholder,parent,false);
        return new MainViewPagerTabLayoutViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewPagerTabLayoutViewHolder holder, int position) {
        HomeModel homeModel = listhomemodels.get(position);
        if( homeModel == null)
        {
            return;
        }
       if (homeModel.getContent() == null || homeModel.getContent().size() == 0 )
       {
           return;
       }
        holder.gifImageView.setText(homeModel.getName());
        PlayingVideoTVAdapter modelAdapter = new PlayingVideoTVAdapter();
        modelAdapter.setData(context, homeModel.getContent());
        holder.recyclerView.setAdapter(modelAdapter);

    }

    @Override
    public int getItemCount() {

        return listhomemodels.size();
    }

    public  class MainViewPagerTabLayoutViewHolder extends  RecyclerView.ViewHolder{

        TextView gifImageView;

        RecyclerView recyclerView;

        public MainViewPagerTabLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            gifImageView = itemView.findViewById(R.id.tv_imageView);
            recyclerView = itemView.findViewById(R.id.inner_recyleview_tablaout);
        }
    }
}
