package com.example.tv360.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tv360.R;
import com.example.tv360.model.ScheduleModel;

import java.util.List;

public class RvScheduleAdapter extends RecyclerView.Adapter<RvScheduleAdapter.RvScheduleViewHolder> {
    Context context;

    List<ScheduleModel> list;

    public  RvScheduleAdapter(Context context, List<ScheduleModel> list)
    {
        this.context =context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvScheduleAdapter.RvScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedules_tv,parent,false);
        return new RvScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvScheduleAdapter.RvScheduleViewHolder holder, int position) {
        ScheduleModel scheduleModel = list.get(position);
        holder.time.setText(scheduleModel.getStartTime());
        holder.name.setText(scheduleModel.getName());
        if(scheduleModel.getPositionPercent() != 0)
        {
            Drawable play = holder.itemView.getContext().getResources().getDrawable( R.drawable.play_schedules);
            holder.display.setBackground(play);
        }
        else
        {
            holder.name.setTextColor(Color.LTGRAY);
            Drawable play = holder.itemView.getContext().getResources().getDrawable( R.drawable.none);
            holder.display.setBackground(play);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  RvScheduleViewHolder  extends RecyclerView.ViewHolder
    {

        TextView time;
        TextView name ;

        ImageButton display ;
        public RvScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.start_time_schedule);
            name = itemView.findViewById(R.id.name_schedule);
            display = itemView.findViewById(R.id.canplay_schedule);
        }
    }
}
