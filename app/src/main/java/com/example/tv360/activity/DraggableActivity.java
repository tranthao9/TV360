package com.example.tv360.activity;

import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tv360.R;
//import com.example.tv360.ui.TV.TVFirstFragment;
import com.example.tv360.ui.TV.TVSecondFragment;
import com.github.pedrovgs.DraggablePanel;


public class DraggableActivity extends AppCompatActivity {

    Button button;
    DraggablePanel draggablePanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draggable);

        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        draggablePanel.setFragmentManager(getSupportFragmentManager());
//        draggablePanel.setTopFragment(new TVFirstFragment());
        draggablePanel.setBottomFragment(new TVSecondFragment());
        draggablePanel.setTopViewHeight(500);
        draggablePanel.initializeView();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                getSupportFragmentManager().popBackStack();
                draggablePanel.closeToLeft();
            }
        },100);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draggablePanel.maximize();
            }
        });
    }
}