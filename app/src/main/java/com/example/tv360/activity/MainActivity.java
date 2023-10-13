package com.example.tv360.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.tv360.R;
import com.example.tv360.adapter.ListFilmAdapter;
import com.example.tv360.adapter.PlayingVideoTVAdapter;
import com.example.tv360.adapter.RvFilmImageAdapter;
import com.example.tv360.ui.TV.DashboardFragment;
//import com.example.tv360.ui.TV.TVFirstFragment;
import com.example.tv360.ui.TV.TVSecondFragment;
import com.example.tv360.ui.home.HomeFragment;
import com.example.tv360.ui.notifications.NotificationsFragment;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements RvFilmImageAdapter.DetailFilmListener, ListFilmAdapter.LoadMoreHomeListener , PlayingVideoTVAdapter.DetailFilmTVListener {



    SharedPreferences sharedPreferences_tv;

    private static  final  String SHARED_TV_PLAYING = "playingtv";

    private  static  final  String KEY_TV = "id";

    private static int key = 0;

    private  DraggablePanel draggablePanel;

    private  BottomNavigationView navView;

    int fragmentnav = 0 ;
    int fragmentbefore  = 0;

    boolean isopened = false;
    HomeFragment fragmenthome;
    DashboardFragment dashboardFragment;
    NotificationsFragment notificationsFragment;

//    TVFirstFragment tvFirstFragment;
    TVSecondFragment tvSecondFragment;
    boolean isdasfragment = false;
    boolean isnotityfragment = false;
    boolean isminimized = false;

    boolean iscloseddraggable = false;

    int getFragmentbefore = 0;

    ImageView searchheader, ProfileHeder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        sharedPreferences_tv = getSharedPreferences(SHARED_TV_PLAYING,MODE_PRIVATE);
        navView = findViewById(R.id.nav_view);
        searchheader = findViewById(R.id.searchHeader);
        ProfileHeder = findViewById(R.id.profileHeader);
        FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();
        fragmenthome = new HomeFragment();
        transaction.replace(R.id.nav_host_fragment_activity_main,fragmenthome);
        transaction.commit();
        searchheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ProfileHeder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, profileActivity.class);
                startActivity(intent);
            }
        });
        fragmentbefore = R.id.navigation_home;
        getFragmentbefore = fragmenthome.getId();
        fragmentnav = navView.getId();
//        tvFirstFragment = new TVFirstFragment();
        tvSecondFragment = new TVSecondFragment();
        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        draggablePanel.setFragmentManager(getSupportFragmentManager());
//        draggablePanel.setTopFragment(tvFirstFragment);
        draggablePanel.setBottomFragment(tvSecondFragment);
        draggablePanel.setTopViewHeight(600);
        draggablePanel.setDraggableListener(new DraggableListener() {

            @Override
            public void onMaximized() {
                if(!isminimized)
                {
                    navView.setSelectedItemId(R.id.navigation_dashboard);
                    navView.setSelected(true);
                }
//                tvFirstFragment.onPlayExo();
            }

            @Override
            public void onMinimized() {
                if(isminimized)
                {
                    navView.setSelectedItemId(fragmentbefore);
                    navView.setSelected(true);
                }
            }
            @Override
            public void onClosedToLeft() {
                isopened = false;
                iscloseddraggable = true;
//                tvFirstFragment.onPauseExo();

            }

            @Override
            public void onClosedToRight() {
                isopened = false;
                iscloseddraggable = true;
//                tvFirstFragment.onPauseExo();
            }

        });


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();
                if(item.getItemId() == R.id.navigation_home)
                {
                    getFragmentbefore = fragmenthome.getId();
                    if(fragmentnav == R.id.navigation_dashboard)
                    {
                        transaction.replace(dashboardFragment.getId(),fragmenthome);
                    }
                    else if(fragmentnav == R.id.navigation_notifications)
                    {
                        transaction.replace(notificationsFragment.getId(),fragmenthome);
                    }
                    else
                    {
                        transaction.replace(fragmenthome.getId(),fragmenthome);
                    }
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragmentnav = R.id.navigation_home;
                    if(isopened)
                    {
                        isminimized = false;
                        draggablePanel.minimize();
                    }
                    fragmentbefore = R.id.navigation_home;
                }
                else if(item.getItemId() == R.id.navigation_notifications)
                {
                    if(!isnotityfragment)
                    {
                        notificationsFragment = new NotificationsFragment();
                        isnotityfragment = true;
                    }
                    if(fragmentnav == R.id.navigation_dashboard)
                    {
                        transaction.replace(dashboardFragment.getId(),notificationsFragment);
                    }
                    else if(fragmentnav == R.id.navigation_notifications)
                    {
                        transaction.replace(notificationsFragment.getId(),notificationsFragment);
                    }
                    else
                    {
                        transaction.replace(fragmenthome.getId(),notificationsFragment);
                    }
                    transaction.addToBackStack(null);
                    transaction.commit();
                    fragmentnav = R.id.navigation_notifications;
                    if(isopened)
                    {
                        isminimized = false;
                        draggablePanel.minimize();
                    }
                    fragmentbefore = R.id.navigation_notifications;
                    getFragmentbefore = notificationsFragment.getId();

                }
                else {

                    if(!isdasfragment)
                    {
                        dashboardFragment = new DashboardFragment();
                        isdasfragment = true;
                    }
                    if(fragmentnav == R.id.navigation_dashboard)
                    {
                        transaction.replace(dashboardFragment.getId(),dashboardFragment);
                    }
                    else if(fragmentnav == R.id.navigation_notifications)
                    {
                        transaction.replace(notificationsFragment.getId(),dashboardFragment);
                    }
                    else
                    {
                        transaction.replace(fragmenthome.getId(),dashboardFragment);
                    }
                    transaction.addToBackStack(null);
                    transaction.commit();
                    if(!isopened)
                    {
                        if (!iscloseddraggable)
                        {
                            draggablePanel.initializeView();
                        }
                        isopened = true;
                    }
                    isminimized = true;
                    draggablePanel.maximize();
                    fragmentnav = R.id.navigation_dashboard;
                    getFragmentbefore = dashboardFragment.getId();
                }
                return  true;
            }
        });
    }

    @Override
    public void detailFilmListener(Intent intent) {
        Intent intent1  = new Intent(MainActivity.this, PlayingVideoAvtivity.class);
        intent1.putExtra("id",intent.getStringExtra("id"));
        intent1.putExtra("type",intent.getStringExtra("type"));
        startActivity(intent1);
    }

    @Override
    public void LoadMoreHomeListener(Intent intent) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent1  = new Intent(MainActivity.this, LoadMoreHomeActivity.class);
                intent1.putExtra("id",intent.getStringExtra("id"));
                startActivity(intent1);
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1000);
    }

    @Override
    public void detailFilmTVListener(Intent intent) {
        SharedPreferences.Editor editor = sharedPreferences_tv.edit();
        editor.putString(KEY_TV, intent.getStringExtra("id"));
        editor.apply();
//        tvFirstFragment.updateData(intent.getStringExtra("id"),intent.getStringExtra("type"));
        tvSecondFragment.updateId(intent.getStringExtra("id"));
    }
}