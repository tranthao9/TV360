package com.example.tv360;

import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


import com.example.tv360.ui.TV.TVFirstFragment;
import com.example.tv360.ui.TV.TVSecondFragment;
import com.github.pedrovgs.DraggablePanel;
public class DraggableActivity extends FragmentActivity {

    Button button;
    DraggablePanel draggablePanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draggable);

        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(new TVFirstFragment());
        draggablePanel.setBottomFragment(new TVSecondFragment());
        draggablePanel.initializeView();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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