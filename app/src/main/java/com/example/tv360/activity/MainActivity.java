package com.example.tv360.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.tv360.R;
import com.example.tv360.adapter.ListFilmAdapter;
import com.example.tv360.adapter.PlayingVideoTVAdapter;
import com.example.tv360.adapter.RvFilmImageAdapter;
import com.example.tv360.ui.TV.DashboardFragment;
import com.example.tv360.ui.TV.TVFirstFragment;
import com.example.tv360.ui.TV.TVSecondFragment;
import com.example.tv360.ui.home.HomeFragment;
import com.example.tv360.ui.notifications.NotificationsFragment;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.util.List;

public class MainActivity extends AppCompatActivity implements RvFilmImageAdapter.DetailFilmListener, ListFilmAdapter.LoadMoreHomeListener , PlayingVideoTVAdapter.DetailFilmTVListener {



    SharedPreferences sharedPreferences_tv;

    private static  final  String SHARED_TV_PLAYING = "playingtv";

    private  static  final  String KEY_TV = "id";

    private static int key = 0;

    private  DraggablePanel draggablePanel;

    private  BottomNavigationView navView;

    int fragmentnav = 0 ;

    boolean isopened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        sharedPreferences_tv = getSharedPreferences(SHARED_TV_PLAYING,MODE_PRIVATE);
        navView = findViewById(R.id.nav_view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//        Fragment exist =(Fragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
//        isopened = false;
//        fragmentnav = 0;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DashboardFragment fragment = new DashboardFragment();
        transaction.replace(R.id.nav_host_fragment_activity_main,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentnav = navView.getId();
        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(new TVFirstFragment());
        draggablePanel.setBottomFragment(new TVSecondFragment());
        draggablePanel.setTopViewHeight(600);
        draggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {

            }

            @Override
            public void onMinimized() {
                getSupportFragmentManager().popBackStack();

            }

            @Override
            public void onClosedToLeft() {
            }

            @Override
            public void onClosedToRight() {
            }
        });
        draggablePanel.initializeView();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId() == R.id.navigation_home)
                {
                    fragmentnav = R.id.navigation_home;
                    if(isopened)
                    {
                        draggablePanel.minimize();
                    }
                }
                if(item.getItemId() == R.id.navigation_notifications)
                {

                    fragmentnav = R.id.navigation_notifications;
                    if(isopened)
                    {
                        draggablePanel.minimize();
                    }

                }
                if(item.getItemId() == R.id.navigation_dashboard) {

                    DashboardFragment fragment = new DashboardFragment();
                    transaction.replace(getSupportFragmentManager().getFragments().get(0).getId(),fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    if(fragmentnav != 0)
                    {
                        draggablePanel.maximize();
                        isopened = true;
                    }
                    fragmentnav = R.id.navigation_dashboard;
                }
                return  false;
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
        Fragment exist =(Fragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        FragmentManager fragmentManager = exist.getChildFragmentManager();
        List<Fragment> list = fragmentManager.getFragments();
        DashboardFragment dashboardFragment = (DashboardFragment) list.get(0);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      dashboardFragment.updateData(intent.getStringExtra("id"));
    }



}