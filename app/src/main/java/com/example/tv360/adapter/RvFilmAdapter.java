package com.example.tv360.adapter;

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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.tv360.R;
import com.example.tv360.model.FilmImageModel;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.ImageModel;
import com.example.tv360.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class RvFilmAdapter extends  RecyclerView.Adapter<RvFilmAdapter.ModelViewHolder>  {

    List<HomeModel> listfilm;

    public RvFilmAdapter(List<HomeModel> homeModels) {
    }

    public  void setData(List<HomeModel> filmModelList)
    {
        this.listfilm = filmModelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_film,parent,false);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        HomeModel filmModel = listfilm.get(position);
        if (filmModel == null){
            return;
        }
        holder.tvModelname.setText(filmModel.getName());
    }

    @Override
    public int getItemCount() {
        if (listfilm != null){
            return listfilm.size();
        }
        return 0;
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder {
        private TextView tvModelname;
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModelname = itemView.findViewById(R.id.film_model);
        }
    }

}
