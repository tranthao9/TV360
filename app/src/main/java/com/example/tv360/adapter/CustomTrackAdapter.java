package com.example.tv360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tv360.R;
import com.example.tv360.TrackSelectionDialog;
import com.example.tv360.model.TrackInfo;
import com.google.android.exoplayer2.util.Log;

import java.util.List;

public class CustomTrackAdapter extends ArrayAdapter<TrackInfo> {

    private Context context;
    private  int resource;
    private  List<TrackInfo> list;
    private  boolean issupportDolby;

    private  int selected;

    private TrackSelectionDialog trackSelectionDialog = new TrackSelectionDialog();
    public CustomTrackAdapter(@NonNull Context context, int resource, @NonNull List<TrackInfo> objects,boolean issupportDolby,int selected) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
        this.issupportDolby = issupportDolby;
        this.selected = selected;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        TrackHolder holder;
        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(resource,parent,false);
            holder = new TrackHolder();
            holder.trackinfor = row.findViewById(R.id.track_info);
            holder.checkInfo = row.findViewById(R.id.checkIcon);
            row.setTag(holder);
        }
        else {
            holder = (TrackHolder) row.getTag();
        }

        TrackInfo trackInfo = list.get(position);
        holder.trackinfor.setText(trackInfo.getFormat());
        if(trackInfo.isIsslect())
        {
            holder.checkInfo.setVisibility(View.VISIBLE);
        }
        else {
            holder.checkInfo.setVisibility(View.GONE);
        }
        if(!issupportDolby)
        {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Khi select ",""+trackInfo.getFormat());
                    Toast.makeText(context, "Bạn đang chạy với " + trackInfo.getFormat(),Toast.LENGTH_SHORT).show();
                    trackSelectionDialog.getData(selected,trackInfo.getPosition(),trackInfo.getFormat());
                }
            });
        }
        else
        {
            if(trackInfo.getFormat().contains("Dolby"))
            {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Khi select ",""+trackInfo.getFormat());
                        Toast.makeText(context, "Bạn đang chạy với " + trackInfo.getFormat(),Toast.LENGTH_SHORT).show();
                        trackSelectionDialog.getData(0,trackInfo.getPosition(),trackInfo.getFormat());

                    }
                });
            }
            else
            {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Bạn đang chạy với " + trackInfo.getFormat(),Toast.LENGTH_SHORT).show();
                        trackSelectionDialog.getData(1,trackInfo.getPosition(),trackInfo.getFormat());
                    }
                });
            }
        }
        return row;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    static class TrackHolder{
        TextView trackinfor;
        ImageView checkInfo;
    }
}
