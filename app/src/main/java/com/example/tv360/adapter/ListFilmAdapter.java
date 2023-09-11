package com.example.tv360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.ListFilmModel;
import com.example.tv360.model.ListFilmModel;

import java.util.ArrayList;
import java.util.List;

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ListFilmViewHolder> {

    private HomeModel mListData;

    private RvFilmAdapter filmAdapter;

    private Context context;
    public void setData( HomeModel listData){

        this.mListData = listData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_film,parent,false);
            return new ListFilmViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_rcv, parent,false);
            return new ListFilmViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListFilmViewHolder holder, int position) {

        if(mListData == null)
        {
            return;
        }
        if(holder.textView != null)
        {
            holder.textView.setText(mListData.getName());

        }
        if(holder.rcvItem != null)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.rcvItem.setLayoutManager(linearLayoutManager);
            holder.rcvItem.setFocusable(false);//ko focus con tro
            RvFilmImageAdapter modelAdapter = new RvFilmImageAdapter();
            modelAdapter.setData(mListData.getContent());
            holder.rcvItem.setAdapter(modelAdapter);
        }
    }

    @Override
    public int getItemCount() {
        if (mListData != null){
            return 2;
        }
        return 0;
    }
    @Override
    public int getItemViewType(int position) {
        return  position ;
    }

    public static class ListFilmViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvItem;

        private  TextView textView;

        public ListFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvItem = itemView.findViewById(R.id.rcv_film);
            textView = itemView.findViewById(R.id.film_model);
        }

    }
}
