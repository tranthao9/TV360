package com.example.tv360.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.HomePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class RvTVAdapter extends RecyclerView.Adapter<RvTVAdapter.RvTVHolder> {

    private  Context context;

    private HomeModel item ;

    private  HomePresenter homePresenter;


    public RvTVAdapter(Context context, HomeModel item) {
        this.homePresenter = new HomePresenter();
        this.context = context;
        this.item = item;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RvTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_tv_load,parent,false);
        return  new RvTVHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RvTVHolder holder, int position) {

        if(item == null)
        {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.rcvtab.setLayoutManager(linearLayoutManager);
        holder.rcvtab.setFocusable(false);//ko focus con tro
        RvTabTVAdapter modelAdapter = new RvTabTVAdapter(context, item.getContent());
        holder.rcvtab.setAdapter(modelAdapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RvTVHolder  extends RecyclerView.ViewHolder {


        private RecyclerView rcvtab;

        public RvTVHolder(@NonNull View itemView) {
            super(itemView);
            rcvtab = itemView.findViewById(R.id.rcv_tab_tv);
        }
    }

}
