package com.example.tv360.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.tv360.R;
import com.example.tv360.adapter.ExpandlabelProfileAdapter;
import com.example.tv360.model.GroupObjectProfile;
import com.example.tv360.model.ItemObjectProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class profileActivity extends AppCompatActivity {

    private ExpandableListView expandableListView ;
    private List<GroupObjectProfile> groupObjectProfileList;

    private Map<GroupObjectProfile, List<ItemObjectProfile>> listitem;

    private ExpandlabelProfileAdapter expandlabelProfileAdapter;

    private int lastExpandPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        expandableListView = findViewById(R.id.expand_profileservices);

        listitem = getListitem();

        groupObjectProfileList = new ArrayList<>(listitem.keySet());

        expandlabelProfileAdapter = new ExpandlabelProfileAdapter(groupObjectProfileList,listitem);
        expandableListView.setAdapter(expandlabelProfileAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return  false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandPosition != -1 && groupPosition != lastExpandPosition) {
                    expandableListView.collapseGroup(lastExpandPosition);
                }
                lastExpandPosition = groupPosition;
            }
        });

        expandableListView.setOnGroupCollapseListener((groupPosition -> {

        }));

    }

    private  Map<GroupObjectProfile,List<ItemObjectProfile>> getListitem()
    {
        Map<GroupObjectProfile,List<ItemObjectProfile>> listMap = new HashMap<>();
        GroupObjectProfile groupObjectProfile = new GroupObjectProfile(1,"Thông tin góp dịch vụ");
        GroupObjectProfile groupObjectProfile2 = new GroupObjectProfile(2,"Danh sách cá nhân");

        List<ItemObjectProfile> list1 = new ArrayList<>();
        list1.add(new ItemObjectProfile(1,"Gói cước"));
        list1.add(new ItemObjectProfile(2,"Nhập mã kích hoạt"));

        List<ItemObjectProfile> list2 = new ArrayList<>();
        list2.add(new ItemObjectProfile(3,"Lịch sử xem"));
        list2.add(new ItemObjectProfile(4,"Yêu thích"));

        listMap.put(groupObjectProfile,list1);
        listMap.put(groupObjectProfile2,list2);
        return  listMap;
    }
}