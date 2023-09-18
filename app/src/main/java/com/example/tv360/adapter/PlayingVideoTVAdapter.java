package com.example.tv360.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tv360.R;
import com.example.tv360.model.FilmModel;

import java.util.List;

public class PlayingVideoTVAdapter extends  RecyclerView.Adapter<PlayingVideoTVAdapter.PlayingVideoTVHolder>{
    private List<FilmModel> categoryList;
    private PlayingVideoTVAdapter.DetailFilmTVListener detailFilmListener;
    private  int display;
    public void setData(Context context, List<FilmModel> list, int display){
        this.categoryList = list;
        this.display = display;
        try {
            this.detailFilmListener = ((PlayingVideoTVAdapter.DetailFilmTVListener)context) ;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(ex.getMessage());
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PlayingVideoTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listfilm, parent,false);
        return new PlayingVideoTVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayingVideoTVHolder holder, int position) {
        FilmModel category = categoryList.get(position);
        if (category == null){
            return;
        }
        if(display == 1)
        {
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(320,400);
            holder.imgCategory.setLayoutParams(l);
        }
        Glide.with(holder.itemView.getContext()).load(category.getCoverImage()).into(holder.imgCategory);
        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",category.getId());
                intent.putExtra("type",category.getType());
                detailFilmListener.detailFilmTVListener(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        if(categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class PlayingVideoTVHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        public PlayingVideoTVHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.listviewfilm);
        }
    }

    public  interface  DetailFilmTVListener
    {
        public  void  detailFilmTVListener(Intent intent);
    }

}
