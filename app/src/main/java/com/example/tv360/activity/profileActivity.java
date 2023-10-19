package com.example.tv360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tv360.R;
import com.example.tv360.TrackSelectionDialog;
import com.example.tv360.adapter.ExpandlabelProfileAdapter;
import com.example.tv360.model.GroupObjectProfile;
import com.example.tv360.model.ItemObjectProfile;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

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

    LinearLayout linearlayout_services_packet,linearLayout5,linearlayout_profilelist,listprofile_detail,linearlayout_setting,linearlayout_setting_detail,setting_quality_video;

    ImageButton button_up_services,button_up_listprofile,button_setting_up;
    boolean isservices = false, islistprofile = false , issetting = false;
    private boolean isShowingTrackSelectionDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        previous = findViewById(R.id.previous_profile);
        text_run = findViewById(R.id.text_run_profile);
        text_run.setHorizontallyScrolling(true);
        text_run.setSelected(true);
        linearlayout_services_packet = findViewById(R.id.linearlayout_services_packet);
        linearLayout5 = findViewById(R.id.linearLayout5);
        button_up_services = findViewById(R.id.button_up_services);
        linearlayout_profilelist = findViewById(R.id.linearlayout_profilelist);
        listprofile_detail = findViewById(R.id.listprofile_detail);
        button_up_listprofile = findViewById(R.id.button_up_listprofile);
        linearlayout_setting = findViewById(R.id.linearlayout_setting);
        linearlayout_setting_detail = findViewById(R.id.linearlayout_setting_detail);
        button_setting_up = findViewById(R.id.button_setting_up);
        setting_quality_video = findViewById(R.id.setting_quality_video);

        setting_quality_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingPlayVideo(Gravity.BOTTOM);
            }
        });

        LinearLayout.LayoutParams layoutParamshide = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        LinearLayout.LayoutParams layoutParamshow = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        linearlayout_services_packet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isservices) {
                    button_up_services.setBackground(getResources().getDrawable(R.drawable.baseline_arrow_drop_down_24_grey));
                    linearLayout5.setLayoutParams(layoutParamshide);
                    isservices = true;
                } else {
                    button_up_services.setBackground(getResources().getDrawable(R.drawable.baseline_arrow_drop_up_24));
                    linearLayout5.setLayoutParams(layoutParamshow);
                    isservices = false;
                }
            }
        });

        linearlayout_profilelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!islistprofile) {
                    button_up_listprofile.setBackground(getResources().getDrawable(R.drawable.baseline_arrow_drop_down_24_grey));
                    listprofile_detail.setLayoutParams(layoutParamshide);
                    islistprofile = true;
                } else {
                    button_up_listprofile.setBackground(getResources().getDrawable(R.drawable.baseline_arrow_drop_up_24));
                    listprofile_detail.setLayoutParams(layoutParamshow);
                    islistprofile = false;
                }
            }
        });

        linearlayout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!issetting) {
                    linearlayout_setting_detail.setLayoutParams(layoutParamshide);
                    button_setting_up.setBackground(getResources().getDrawable(R.drawable.baseline_arrow_drop_down_24_grey));
                    issetting = true;
                } else {
                    linearlayout_setting_detail.setLayoutParams(layoutParamshow);
                    button_setting_up.setBackground(getResources().getDrawable(R.drawable.baseline_arrow_drop_up_24));
                    issetting = false;
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  void  openSettingPlayVideo(int gravity)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_settting_quality_video);
        Window window = dialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttribute = window.getAttributes();
        windownAttribute.gravity = gravity;
        window.setAttributes(windownAttribute);

        if(Gravity.BOTTOM == gravity)
        {
            dialog.setCancelable(true);
        }
        else
        {
            dialog.setCancelable(false);
        }
        dialog.show();
        RadioButton radioButton = dialog.findViewById(R.id.radio_auto);
        RadioButton radioButton_normal = dialog.findViewById(R.id.radio_normal);
        RadioButton radioButton_better = dialog.findViewById(R.id.radio_better);
        RadioButton radioButton_dolby = dialog.findViewById(R.id.radio_dolby);
        radioButton.setChecked(true);
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.red80));
        radioButton.setButtonTintList(colorStateList);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.red80));
                    radioButton.setButtonTintList(colorStateList);
                }
                else
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
                    radioButton.setButtonTintList(colorStateList);
                }

            }
        });

        radioButton_normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.red80));
                    radioButton_normal.setButtonTintList(colorStateList);
                }
                else
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
                    radioButton_normal.setButtonTintList(colorStateList);
                }

            }
        });

        radioButton_better.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.red80));
                    radioButton_better.setButtonTintList(colorStateList);
                }
                else
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
                    radioButton_better.setButtonTintList(colorStateList);
                }

            }
        });

        radioButton_dolby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.red80));
                    radioButton_dolby.setButtonTintList(colorStateList);
                }
                else
                {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
                    radioButton_dolby.setButtonTintList(colorStateList);
                }

            }
        });

    }
}