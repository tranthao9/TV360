package com.example.tv360.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tv360.LoginActivity;
import com.example.tv360.MainActivity;
import com.example.tv360.PlayingVideoAvtivity;
import com.example.tv360.R;
import com.example.tv360.model.FilmImageModel;
import com.example.tv360.model.FilmModel;

import java.util.List;

public class RvFilmImageAdapter extends  RecyclerView.Adapter<RvFilmImageAdapter.RvFilmImageViewHolder>{

    private List<FilmModel> categoryList;
    private  DetailFilmListener detailFilmListener;
    public void setData(Context context, List<FilmModel> list){
        this.categoryList = list;
        try {
            this.detailFilmListener = ((DetailFilmListener)context) ;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(ex.getMessage());
        }
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
        FilmModel category = categoryList.get(position);
        if (category == null){
            return;
        }
        Glide.with(holder.itemView.getContext()).load(category.getCoverImage()).into(holder.imgCategory);
        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",category.getId());
                intent.putExtra("type",category.getType());
                detailFilmListener.detailFilmListener(intent);
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

    public class RvFilmImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        public RvFilmImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.listviewfilm);
        }
    }

    public  interface  DetailFilmListener
    {
        public  void  detailFilmListener(Intent intent);
    }
}
