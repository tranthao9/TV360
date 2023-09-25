package com.example.tv360.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.tv360.R;
import com.example.tv360.model.GroupObjectProfile;
import com.example.tv360.model.ItemObjectProfile;

import java.util.List;
import java.util.Map;

public class ExpandlabelProfileAdapter extends BaseExpandableListAdapter {
    private List<GroupObjectProfile> listgroup;
    private Map<GroupObjectProfile,List<ItemObjectProfile>> mlistItem;

    public ExpandlabelProfileAdapter(List<GroupObjectProfile> listgroup, Map<GroupObjectProfile, List<ItemObjectProfile>> mlistItem) {
        this.listgroup = listgroup;
        this.mlistItem = mlistItem;
    }

    @Override
    public int getGroupCount() {
        if(listgroup != null)
        {
            return  listgroup.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(listgroup != null && mlistItem != null)
        {
            return  mlistItem.get(listgroup.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listgroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mlistItem.get(listgroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        GroupObjectProfile groupObjectProfile = listgroup.get(groupPosition);
        return groupObjectProfile.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        ItemObjectProfile itemObjectProfile = mlistItem.get(listgroup.get(groupPosition)).get(childPosition);
        return itemObjectProfile.getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_group_profileservices,parent,false);
        }
        TextView textViewgroup = convertView.findViewById(R.id.information_services);
        textViewgroup.setText(listgroup.get(groupPosition).getName());
        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_profileservices,parent,false);
        }
        TextView textViewItem = convertView.findViewById(R.id.information_services_item);
        textViewItem.setText(mlistItem.get(listgroup.get(groupPosition)).get(childPosition).getName());
        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
