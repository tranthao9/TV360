package com.example.tv360.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Objects;

public class RvSearchImageAdapter extends  RecyclerView.Adapter<RvSearchImageAdapter.RvSearchImageViewHolder>{

    private List<FilmModel> categoryList;
    private  DetailSearchListener detailFilmListener;
    private  String display;

    public void setData(Context context, List<FilmModel> list, String display){
        this.categoryList = list;
        this.display = display;
        try {
            this.detailFilmListener = ((DetailSearchListener)context) ;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(ex.getMessage());
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RvSearchImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listfilm, parent,false);
        return new RvSearchImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvSearchImageViewHolder holder, int position) {
        FilmModel category = categoryList.get(position);
        if (category == null){
            return;
        }
        if(!Objects.equals(display, "VOD"))
        {
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(290,400);
            holder.imgCategory.setLayoutParams(l);
        }
        holder.imgCategory.setClipToOutline(true);
        Glide.with(holder.itemView.getContext()).load(category.getCoverImage()).into(holder.imgCategory);
        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",category.getId());
                intent.putExtra("type",category.getType());
                detailFilmListener.DetailSearchListener(intent);
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

    public class RvSearchImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;

        public RvSearchImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.listviewfilm);

        }
    }

    public  interface  DetailSearchListener
    {
        public  void  DetailSearchListener(Intent intent);
    }
}
