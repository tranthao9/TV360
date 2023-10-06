package com.example.tv360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;

import java.util.List;

public class SearchContent_2_Adapter extends RecyclerView.Adapter<SearchContent_2_Adapter.SearchContent_2_ViewHolder> {
    Context context;
    List<String> searchContentModel;
    public SearchContent_2_Adapter(Context context, List<String>  searchContentModel) {
        this.context = context;
        this.searchContentModel = searchContentModel;
    }

    @NonNull
    @Override
    public SearchContent_2_Adapter.SearchContent_2_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_view_content2,parent,false);
        return new SearchContent_2_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchContent_2_Adapter.SearchContent_2_ViewHolder holder, int position) {
        if (searchContentModel.get(position) == null)
        {
            return;
        }
        holder.textView.setText(searchContentModel.get(position));
    }

    @Override
    public int getItemCount() {
        return searchContentModel.size();
    }

    public class SearchContent_2_ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public SearchContent_2_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.search_content_2_name);
        }
    }
}
