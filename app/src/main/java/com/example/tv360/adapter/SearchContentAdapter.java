package com.example.tv360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.SearchContentModel;

import java.util.List;

public class SearchContentAdapter extends RecyclerView.Adapter<SearchContentAdapter.SearchContentViewHolder> {
    private  Context context;
    private  List<SearchContentModel> searchContentAdapterList;

    public SearchContentAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    public  void SetData( List<SearchContentModel> list)
    {
        this.searchContentAdapterList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchContentAdapter.SearchContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_view,parent,false);
        return new SearchContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchContentAdapter.SearchContentViewHolder holder, int position) {
        if(searchContentAdapterList.get(position) == null)
        {
            return;
        }
        SearchContentModel searchContent = searchContentAdapterList.get(position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setFocusable(false);//ko focus con tro
        holder.textView.setText(searchContent.getName());
        if (position == 0)
        {
           SearchContent_1_Adapter searchContent1Adapter = new SearchContent_1_Adapter(context,searchContent.getContent());
           holder.recyclerView.setAdapter(searchContent1Adapter);
        }
        else
        {
            SearchContent_2_Adapter searchContent2Adapter = new SearchContent_2_Adapter(context,searchContent.getContent());
            holder.recyclerView.setAdapter(searchContent2Adapter);
        }

    }

    @Override
    public int getItemCount() {
        return searchContentAdapterList.size();
    }

    public class SearchContentViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private  RecyclerView recyclerView;
        public SearchContentViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerViewSearchDetail);
            textView = itemView.findViewById(R.id.name_search);
        }
    }
}
