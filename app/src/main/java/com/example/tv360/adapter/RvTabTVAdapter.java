package com.example.tv360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.FilmModel;

import java.util.List;

public class RvTabTVAdapter extends RecyclerView.Adapter<RvTabTVAdapter.RvTabTVViewHolder> {
    Context context;
    List<FilmModel> filmModels;

    public  RvTabTVAdapter(Context context, List<FilmModel> listitem)
    {
        this.context = context;
        this.filmModels = listitem;
    }
    @NonNull
    @Override
    public RvTabTVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_name, parent,false);
        return new RvTabTVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvTabTVViewHolder holder, int position) {
        FilmModel  filmModel = filmModels.get(position);
        if(filmModel == null)
        {
            return;
        }
        holder.rcvData.setText(filmModel.getName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RvTabTVViewHolder extends  RecyclerView.ViewHolder {
        public TextView rcvData;
        public RvTabTVViewHolder(@NonNull View itemView  ) {
            super(itemView);
            rcvData =  itemView.findViewById(R.id.tab_name_tv);

        }
    }
}
