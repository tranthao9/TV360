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
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class RvTVAdapter extends RecyclerView.Adapter<RvTVAdapter.RvTVHolder> {

    private  Context context;

    private  List<HomeModel> item ;

    private  HomePresenter homePresenter;

    private  TVListener tvListener;

    public RvTVAdapter(Context context, List<HomeModel> item) {
        this.homePresenter = new HomePresenter();
        this.context = context;
        this.item = item;

        try {
            this.tvListener = ((RvTVAdapter.TVListener)context) ;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(ex.getMessage());
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RvTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videotv,parent,false);
            return  new RvTVHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_tv_load,parent,false);
            return  new RvTVHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RvTVHolder holder, int position) {
        HomeModel homeModel = item.get(position);
        if(homeModel == null)
        {
            return;
        }
        if(holder.rcvVideo != null)
        {
            Intent intent = new Intent();
            intent.putExtra("id",item.get(0).getContentPlaying().getDetail().getId());
            intent.putExtra("type",item.get(0).getContentPlaying().getDetail().getType());
            tvListener.TVListener(intent);
        }
       if (holder.rcvtab != null)
       {
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
           holder.rcvtab.setLayoutManager(linearLayoutManager);
           holder.rcvtab.setFocusable(false);//ko focus con tro
           RvTabTVAdapter modelAdapter = new RvTabTVAdapter(context, item.get(1).getContent());
           holder.rcvtab.setAdapter(modelAdapter);
       }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class RvTVHolder  extends RecyclerView.ViewHolder {

        private StyledPlayerView rcvVideo;

        private RecyclerView rcvtab;

        public RvTVHolder(@NonNull View itemView) {
            super(itemView);
            rcvVideo = itemView.findViewById(R.id.playvideo_tv);
            rcvtab = itemView.findViewById(R.id.rcv_tab_tv);
        }
    }

    public  interface  TVListener
    {
        public  void  TVListener(Intent intent);
    }
}
