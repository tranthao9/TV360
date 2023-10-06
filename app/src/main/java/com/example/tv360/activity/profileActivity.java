package com.example.tv360.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

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

    ImageButton previous;

    TextView text_run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        previous = findViewById(R.id.previous_profile);
        text_run = findViewById(R.id.text_run_profile);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text_run.requestFocus();
                text_run.setSelected(true);

            }
        },100);

//        final  int textviewWidth = text_run.getWidth();
//        final  int screenWidth = getResources().getDisplayMetrics().widthPixels;
//        TranslateAnimation translateAnimation = new TranslateAnimation(
//              0,screenWidth ,0,0
//        );
//        translateAnimation.setDuration(6000);
//        translateAnimation.setRepeatCount(Animation.INFINITE);
//        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                text_run.clearAnimation();
//                text_run.setTranslationX(0);
//                text_run.startAnimation(translateAnimation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        text_run.startAnimation(translateAnimation);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        expandableListView = findViewById(R.id.expand_profileservices);

        listitem = getListitem();

//        groupObjectProfileList = new ArrayList<>(listitem.keySet());
//
//        expandlabelProfileAdapter = new ExpandlabelProfileAdapter(groupObjectProfileList,listitem);
//        expandableListView.setAdapter(expandlabelProfileAdapter);
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//
//                return  false;
//            }
//        });
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                return false;
//            }
//        });
//
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastExpandPosition != -1 && groupPosition != lastExpandPosition) {
//                    expandableListView.collapseGroup(lastExpandPosition);
//                }
//                lastExpandPosition = groupPosition;
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener((groupPosition -> {
//
//        }));

    }

    private  Map<GroupObjectProfile,List<ItemObjectProfile>> getListitem()
    {
        Map<GroupObjectProfile,List<ItemObjectProfile>> listMap = new HashMap<>();
        GroupObjectProfile groupObjectProfile = new GroupObjectProfile(1,"Thông tin góp dịch vụ");
        GroupObjectProfile groupObjectProfile2 = new GroupObjectProfile(2,"Danh sách cá nhân");
        GroupObjectProfile groupObjectProfile3 = new GroupObjectProfile(3,"Cài đặt");

        List<ItemObjectProfile> list1 = new ArrayList<>();
        list1.add(new ItemObjectProfile(1,"Gói cước"));
        list1.add(new ItemObjectProfile(2,"Nhập mã quà tặng"));

        List<ItemObjectProfile> list2 = new ArrayList<>();
        list2.add(new ItemObjectProfile(3,"Lịch sử xem"));
        list2.add(new ItemObjectProfile(4,"Yêu thích"));

        List<ItemObjectProfile> list3 = new ArrayList<>();

        listMap.put(groupObjectProfile,list1);
        listMap.put(groupObjectProfile2,list2);
        return  listMap;
    }
}