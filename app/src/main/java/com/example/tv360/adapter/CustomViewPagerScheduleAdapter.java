package com.example.tv360.adapter;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tv360.R;
import com.example.tv360.listener.ImageViewTVListener;
import com.example.tv360.model.FilmModel;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CustomViewPagerScheduleAdapter extends RecyclerView.Adapter<CustomViewPagerScheduleAdapter.CustomViewPagerScheduleViewHolder>  {
    private List<FilmModel> categoryList;

    private DetailScheduleListener detailScheduleListener;
    private  Context context;

    private String id = "";
    private PlayingVideoTVAdapter.DetailFilmTVListener detailFilmListener;

    private List<View.OnClickListener>  clickListeners = new ArrayList<>();

    public void setData(Context context, List<FilmModel> list , String id){
        this.categoryList = list;
        this.context = context;
        id = id;
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
    public CustomViewPagerScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listfilm, parent,false);
        return new CustomViewPagerScheduleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewPagerScheduleViewHolder holder, int position) {
        FilmModel category = categoryList.get(position);
        if (category == null){
            return;
        }
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(200,120);
        holder.imgCategory.setLayoutParams(l);
        holder.imgCategory.setClipToOutline(true);
        Glide.with(holder.itemView.getContext()).load(category.getCoverImage()).into(holder.imgCategory);
        Log.d("position"+position,"no onclick");
        Log.d("id"+id,"no onclick");
        Log.d("id"+category.getId(),"no onclick");
        if(id == category.getId())
        {
            Drawable highlight = holder.itemView.getContext().getResources().getDrawable( R.drawable.highlight);
            holder.imgCategory.setBackground(highlight);
        }
        else
        {
            Drawable border = holder.itemView.getContext().getResources().getDrawable( R.drawable.border);
            holder.imgCategory.setBackground(border);
        }
//        if(!arraya.get(position))
//        {
//
//        }
//        if(arraya.get(position) && !Objects.equals(id, category.getId())){
//
//            Log.d("position no "+arraya.clone(),"no1 onclick");
//            arraya.put(position,false);
//            Log.d("position no "+arraya.clone(),"afterclear" + arraya.clone());
//        }
        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = holder.itemView.getContext().getResources().getDrawable( R.drawable.highlight);
                holder.imgCategory.setBackground(highlight);
                id = category.getId();
                notifyDataSetChanged();
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

    public class CustomViewPagerScheduleViewHolder extends RecyclerView.ViewHolder  {
        private ImageView imgCategory;

        public CustomViewPagerScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.listviewfilm);
        }

    }



    public  interface  DetailScheduleListener
    {
        public  void  DetailScheduleListener(Intent intent);
    }
}
