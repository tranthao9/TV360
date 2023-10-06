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
import com.example.tv360.model.SearchContentModel;

import java.util.List;

public class SearchContent_1_Adapter extends RecyclerView.Adapter<SearchContent_1_Adapter.SearchContent_1_ViewHolder> {
    Context context;
    List<String> searchContentModel;
    public SearchContent_1_Adapter(Context context,  List<String>  searchContentModel) {
        this.context = context;
        this.searchContentModel = searchContentModel;
    }

    @NonNull
    @Override
    public SearchContent_1_Adapter.SearchContent_1_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view_content1,parent,false);
        return new SearchContent_1_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchContent_1_Adapter.SearchContent_1_ViewHolder holder, int position) {
        if (searchContentModel.get(position) == null)
        {
            return;
        }
        holder.textView.setText(searchContentModel.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return searchContentModel.size();
    }

    public class SearchContent_1_ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public SearchContent_1_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.search_content_1_name);
            imageView = itemView.findViewById(R.id.search_content_1_remove);
        }
    }
}
