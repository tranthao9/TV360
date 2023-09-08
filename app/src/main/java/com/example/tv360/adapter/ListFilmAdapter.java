package com.example.tv360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.ListFilmModel;
import com.example.tv360.model.ListFilmModel;

import java.util.List;

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ListFilmViewHolder> {


    public static  final int TYPE_CATEGORY = 2;
    public static  final int TYPE_MODEL = 1;
    private List<ListFilmModel> mListData;
    private Context context;
    public void setData(Context context1, List<ListFilmModel> listData){
        this.context = context1;
        this.mListData = listData;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return mListData.get(position).getType();
    }

    @NonNull
    @Override
    public ListFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_rcv,parent,false);
        return new ListFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFilmViewHolder holder, int position) {
        ListFilmModel listData = mListData.get(position);
        if (listData == null){
            return;
        }
        if (TYPE_MODEL == holder.getItemViewType()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
            holder.rcvItem.setLayoutManager(linearLayoutManager);
            holder.rcvItem.setFocusable(false);//ko focus con tro

            RvFilmAdapter modelAdapter = new RvFilmAdapter();
            modelAdapter.setData(listData.getLisfilm());

            holder.rcvItem.setAdapter(modelAdapter);

        } else if (
                TYPE_CATEGORY == holder.getItemViewType()){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.rcvItem.setLayoutManager(linearLayoutManager);
            holder.rcvItem.setFocusable(false);//ko focus con tro

            RvFilmImageAdapter  categoryAdapter = new RvFilmImageAdapter();
            categoryAdapter.setData(listData.getListfilmimage());

            holder.rcvItem.setAdapter(categoryAdapter);
        }
    }

    @Override
    public int getItemCount() {
        if (mListData != null){
            return mListData.size();
        }
        return 0;
    }
    public static class ListFilmViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView rcvItem;
        public ListFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvItem = itemView.findViewById(R.id.rcv_film);
        }
    }
}
