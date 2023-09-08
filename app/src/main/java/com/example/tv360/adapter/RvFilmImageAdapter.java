package com.example.tv360.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.FilmImageModel;

import java.util.List;

public class RvFilmImageAdapter extends  RecyclerView.Adapter<RvFilmImageAdapter.RvFilmImageViewHolder>{

    private List<FilmImageModel> categoryList;
    public void setData(List<FilmImageModel> list){
        this.categoryList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RvFilmImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listfilm, parent,false);
        return new RvFilmImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvFilmImageViewHolder holder, int position) {
        FilmImageModel category = categoryList.get(position);
        if (category == null){
            return;
        }
        holder.imgCategory.setImageResource(category.getImgFilm());
    }

    @Override
    public int getItemCount() {
        if(categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class RvFilmImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        public RvFilmImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.listviewfilm);
        }
    }
}
