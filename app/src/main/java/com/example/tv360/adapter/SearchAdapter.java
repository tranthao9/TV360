package com.example.tv360.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.HomeModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    List<HomeModel> listhome;

    public SearchAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    public  void SetData(List<HomeModel> list)
    {
        this.listhome = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        if(listhome.get(position) == null)
        {
            return;
        }
        HomeModel mListData = listhome.get(position);
        holder.rcvItem.setText(mListData.getName());
        RvSearchImageAdapter modelAdapter = new RvSearchImageAdapter();
        modelAdapter.setData(context, mListData.getContent(),mListData.getType());
        holder.serch_detail.setAdapter(modelAdapter);
    }

    @Override
    public int getItemCount() {

        return listhome.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView rcvItem;

        private RecyclerView serch_detail;
        public SearchViewHolder(@NonNull View itemView  ) {
            super(itemView);
            rcvItem = itemView.findViewById(R.id.name_search);
            serch_detail = itemView.findViewById(R.id.recyclerViewSearchDetail);
        }
    }
}
