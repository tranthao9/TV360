package com.example.tv360.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tv360.R;
import com.example.tv360.model.FilmModel;

import java.util.List;


public class SearchSuggestAdapter extends RecyclerView.Adapter<SearchSuggestAdapter.SearchSuggestViewHolder> {
    Context context;
    private DetailFilmListener  detailFilmListener;

    List<FilmModel> modelList;

    public  interface  DetailFilmListener
    {
        public  void  detailFilmListener(Intent intent);
    }

    public SearchSuggestAdapter(Context context) {
        this.context = context;
        this.modelList = modelList;
        try {
            this.detailFilmListener = ((DetailFilmListener)context) ;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(ex.getMessage());
        }
        notifyDataSetChanged();

    }

    public  void  SetData(List<FilmModel> homeModels)
    {
        this.modelList = homeModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchSuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.search_suggest,parent,false);
        return new SearchSuggestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestViewHolder holder, int position) {
        if(modelList.get(position) == null)
        {
            return;
        }
        FilmModel filmModel = modelList.get(position);
        holder.imageView.setClipToOutline(true);
        Glide.with(holder.itemView.getContext()).load(filmModel.getCoverImage()).into(holder.imageView);
        holder.textViewname.setText(filmModel.getName());
        holder.textViewwatched.setText(filmModel.getPlayTimes() + " lượt xem");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",filmModel.getId());
                intent.putExtra("type",filmModel.getType());
                detailFilmListener.detailFilmListener(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(modelList != null)
        {
            return  modelList.size();
        }
        return 0;
    }

    public  class  SearchSuggestViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView ;
        private TextView textViewname;
        private  TextView textViewwatched;

        public SearchSuggestViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_search_suggest);
            textViewname = itemView.findViewById(R.id.name_search_suggest);
            textViewwatched = itemView.findViewById(R.id.watched_search_suggest);

        }
    }
}
