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

public class CustomViewPagerScheduleAdapter extends RecyclerView.Adapter<CustomViewPagerScheduleAdapter.CustomViewPagerScheduleViewHolder> {
    private List<FilmModel> categoryList;
    private DetailScheduleListener detailScheduleListener;
    public void setData(Context context, List<FilmModel> list){
        this.categoryList = list;
//        try {
//            this.detailScheduleListener = ((CustomViewPagerScheduleAdapter.DetailScheduleListener)context) ;
//        }catch (ClassCastException ex)
//        {
//            throw new ClassCastException(ex.getMessage());
//        }
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
//        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("id",category.getId());
//                intent.putExtra("type",category.getType());
//                detailFilmListener.detailFilmListener(intent);
//            }
//
//        });
    }

    @Override
    public int getItemCount() {
        if(categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class CustomViewPagerScheduleViewHolder extends RecyclerView.ViewHolder {
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
