package com.example.tv360.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.tv360.R;
import com.example.tv360.model.HomeModel;


import java.util.logging.LogRecord;

public class LoadMoreAdapter extends BaseAdapter {
    private  Context context;
    private  HomeModel homeModel;
    LayoutInflater inflter;
    public LoadMoreAdapter(Context context, HomeModel homeModel) {
        super();
        this.context = context;
        this.homeModel = homeModel;
        inflter	=	LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return homeModel.getContent().size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView	=	inflter.inflate(R.layout.activity_load_more_home, null);
        ImageView icon	=	(ImageView) convertView.findViewById(R.id.icon);
        if(homeModel.getDisplay() == 0)
        {
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,320);

            convertView.setLayoutParams(l);
        }
        Glide.with(parent.getContext()).load(homeModel.getContent().get(position).getCoverImage()).into(icon);

        return convertView;
    }
}
