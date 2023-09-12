package com.example.tv360;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tv360.adapter.RvFilmImageAdapter;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectUrlVideo;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayingVideoAvtivity extends AppCompatActivity{

    ExoPlayer player;
    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    private ImageButton exo_pause;
    private ImageButton exo_play;

    ImageView fullscreen;

    Boolean isfullscreen = false;
    HomeService apiserver ;

    ProgressBar progressBar;

    Boolean ispause = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_video_avtivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObjectUrlVideo> data = apiserver.getlinka(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"));

        data.enqueue(new Callback<DataObjectUrlVideo>() {
            @Override
            public void onResponse(Call<DataObjectUrlVideo> call, Response<DataObjectUrlVideo> response) {
                DataObjectUrlVideo urlVideo = response.body();
                Log.d("TAG : " + response.body(),"ok");
                StyledPlayerView styledPlayerView = findViewById(R.id.playvideo);
                fullscreen = styledPlayerView.findViewById(R.id.exo_fullscreen_icon);
                TextView info = findViewById(R.id.textView);
//                info.setText(urlVideo.getData().);
                progressBar = findViewById(R.id.progressBar);

                ImageButton play = styledPlayerView.findViewById(R.id.pause_button);
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ispause == false)
                        {
                            ispause = true;
                            play.setBackgroundResource(R.drawable.baseline_play_arrow_24);
                            player.setPlayWhenReady(false);
                            player.pause();
                        }
                        else
                        {
                            ispause = false;
                            play.setBackgroundResource(R.drawable.baseline_pause_24);
                            player.setPlayWhenReady(true);
                            player.play();


                        }

                    }
                });

                fullscreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isfullscreen){

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                            if(getSupportActionBar() != null){
                                getSupportActionBar().show();
                            }

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) styledPlayerView.getLayoutParams();
                            params.width = params.MATCH_PARENT;
                            params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                            styledPlayerView.setLayoutParams(params);
                            isfullscreen = false;
                        }else {

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                            if(getSupportActionBar() != null){
                                getSupportActionBar().hide();
                            }

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) styledPlayerView.getLayoutParams();
                            params.width = params.MATCH_PARENT;
                            params.height = params.MATCH_PARENT;
                            styledPlayerView.setLayoutParams(params);
                            isfullscreen = true;
                        }
                    }
                });
                player = new ExoPlayer.Builder(PlayingVideoAvtivity.this).build();
                styledPlayerView.setPlayer(player);

                MediaItem mediaItem = MediaItem.fromUri(urlVideo.getData().getUrlStreaming());
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true);
                player.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState) {
                        Player.Listener.super.onPlaybackStateChanged(playbackState);
                        if(playbackState == Player.STATE_READY){
                            progressBar.setVisibility(View.GONE);
                            player.setPlayWhenReady(true);
                        }else if(playbackState == Player.STATE_BUFFERING){
                            progressBar.setVisibility(View.VISIBLE);
                            styledPlayerView.setKeepScreenOn(true);
                        }else {
                            progressBar.setVisibility(View.GONE);
                            player.setPlayWhenReady(true);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<DataObjectUrlVideo> call, Throwable t) {
                call.cancel();
            }
        });
    }


    @Override
    protected void  onStop()
    {
        super.onStop();
        player.setPlayWhenReady(false);

    }



}