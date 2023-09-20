package com.example.tv360;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import com.example.tv360.adapter.ListFilmAdapter;
import com.example.tv360.adapter.PlayingVideoTVAdapter;
import com.example.tv360.adapter.RvFilmImageAdapter;
import com.example.tv360.adapter.RvTVAdapter;
import com.example.tv360.ui.TV.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tv360.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RvFilmImageAdapter.DetailFilmListener, ListFilmAdapter.LoadMoreHomeListener , PlayingVideoTVAdapter.DetailFilmTVListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
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

        Fragment exist =(Fragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        FragmentManager fragmentManager = exist.getChildFragmentManager();
        List<Fragment> list = fragmentManager.getFragments();
        DashboardFragment dashboardFragment = (DashboardFragment) list.get(0);
        dashboardFragment.updateData(intent.getStringExtra("id"),intent.getStringExtra("type"));
    }
}