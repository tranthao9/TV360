package com.example.tv360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tv360.R;
import com.example.tv360.TrackSelectionDialog;
import com.example.tv360.checkvideo.VideoCodecChecker;
import com.example.tv360.model.DataObjectUrlVideo;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.MimeTypes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayingVideoAvtivity extends AppCompatActivity{

    ExoPlayer player;
    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    String[] speed = {"0.25x", "0.5x", "Normal", "1.5x", "2x"};
    private boolean isShowingTrackSelectionDialog = false;
    private DefaultTrackSelector trackSelector;

    ImageView fullscreen;

    Boolean isfullscreen = false;
    HomeService apiserver ;

    ProgressBar progressBar;

    Boolean ispause = false;

    TextView text_speed;

    TextView text_quality;

    StyledPlayerView styledPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_playing_video_avtivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        apiserver = ApiService.getlinknocontenttype(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObjectUrlVideo> data  = apiserver.getlinka(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"));
        data.enqueue(new Callback<DataObjectUrlVideo>() {
            @Override
            public void onResponse(Call<DataObjectUrlVideo> call, Response<DataObjectUrlVideo> response) {
                DataObjectUrlVideo urlVideo = response.body();
                styledPlayerView = findViewById(R.id.playvideo);
                fullscreen = styledPlayerView.findViewById(R.id.exo_fullscreen_icon);
                TextView info = findViewById(R.id.textView);
//                info.setText(urlVideo.getData().);
                progressBar = findViewById(R.id.progressBar);

                ImageView setiing_play = styledPlayerView.findViewById(R.id.exo_settings_icon);

                ImageView play = styledPlayerView.findViewById(R.id.pause_button);
                setiing_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSettingPlayVideo(Gravity.BOTTOM);
                    }
                });
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!ispause)
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

                trackSelector = new DefaultTrackSelector(PlayingVideoAvtivity.this);
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                Log.d("mapinfor ", "" + mappedTrackInfo);
//                if(mappedTrackInfo != null)
//                {
//                    DefaultTrackSelector.ParametersBuilder builder = trackSelector.buildUponParameters();
//                    for (int rendererIndex = 0; rendererIndex < mappedTrackInfo.getRendererCount();rendererIndex ++)
//                    {
//                        TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex);
//                        if(trackGroupArray.length == 0)
//                        {
//                            continue;
//                        }
//                        List<Integer> selectedTracks = new ArrayList<>();
//                        for (int groupindex =0;groupindex<trackGroupArray.length;groupindex++)
//                        {
//                           TrackGroup trackGroup = trackGroupArray.get(groupindex);
//                           for (int trackindex = 0; trackindex<trackGroup.length;trackindex++)
//                           {
//                               Format format = trackGroup.getFormat(trackindex);
//                               if(isSupportQuality(format))
//                               {
//                                   selectedTracks.add(trackindex);
//                                   DefaultTrackSelector.Parameters parameters = builder.setSelectionOverride(
//                                           rendererIndex,
//                                           trackGroupArray,
//                                           new DefaultTrackSelector.SelectionOverride(groupindex)
//                                   ).build();
//                                   trackSelector.setParameters(parameters);
//                               }
//                           }
//
//                        }
//                    }
//                }
//
//
                TrackSelectionParameters newParameters = trackSelector.getParameters()
                        .buildUpon()
                        .setForceHighestSupportedBitrate(true)
                        .build();

//                TrackSelectionParameters trackSelectionParameters = new TrackSelectionParameters.Builder()
//                        .setPreferredTextLanguage("en")
//                                .setPreferredAudioLanguage("en")
//                                        .build();
//
                trackSelector.setParameters((DefaultTrackSelector.Parameters)newParameters);
                player = new ExoPlayer.Builder(PlayingVideoAvtivity.this).setTrackSelector(trackSelector).build();
//                player = new ExoPlayer.Builder(PlayingVideoAvtivity.this).setTrackSelector(trackSelector).build();
                styledPlayerView.setPlayer(player);
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                Log.d("mapinfor ", "" + mappedTrackInfo);
                MediaItem mediaItem = MediaItem.fromUri("http://cdn-vttvas.s3.cloudstorage.com.vn/video1/dv/output/stream.mpd");
               // MediaItem mediaItem = MediaItem.fromUri(urlVideo.getData().getUrlStreaming());
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true);
                player.play();
                Log.d("Tag Dolby ", " "+ VideoCodecChecker.issupportdolby());

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
                player.addListener(new Player.Listener() {
                    @Override
                    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                        Player.Listener.super.onTracksChanged(trackGroups, trackSelections);
                        if(trackGroups.length > 0)
                        {
                            TrackGroup trackGroup = trackGroups.get(0);
                            for (int i=0;i<trackGroup.length;i++)
                            {
                                Format format = trackGroup.getFormat(i);
                                if(MimeTypes.isVideo(format.sampleMimeType))
                                {
                                    String codec = format.codecs;
                                    Toast.makeText(PlayingVideoAvtivity.this,"codec "+codec,Toast.LENGTH_SHORT).show();
                                    Log.d("Tag Dolby codec ", " "+ codec);
                                }
                            }
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

    private  boolean isSupportQuality(Format format)
    {
        if(format.width > 1920 || format.height > 1080)
        {
            return  false;
        }
        if(format.pixelWidthHeightRatio < 1.0f || format.pixelWidthHeightRatio > 2.0f)
        {
            return  false;
        }

        return  true;
    }

    @Override
    protected void  onStop()
    {
        super.onStop();
        styledPlayerView.setPlayer(null);
        player.setPlayWhenReady(false);
        player.release();
        player = null;

    }

    private  void  openSettingPlayVideo(int gravity)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_settting);
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
        LinearLayoutCompat speedBtn = dialog.findViewById(R.id.exo_playback_speed);

        LinearLayoutCompat qualitiesbtn = dialog.findViewById(R.id.exo_playback_quality);
        text_speed = dialog.findViewById(R.id.exo_playback_speed_content);

        text_quality = dialog.findViewById(R.id.exo_playback_quality_content);
        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSpeed();
            }
        });


        qualitiesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isShowingTrackSelectionDialog && TrackSelectionDialog.willHaveContent(trackSelector)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(trackSelector,
                            /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
                    player.addListener(new Player.Listener() {

                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                            Player.Listener.super.onTracksChanged(trackGroups, trackSelections);
                            if (trackSelections.get(0) != null) {
                                text_quality.setText(trackSelections.get(0).getFormat(0).height +"p");
                            }
                        }

                    });
                }
            }


        });
        dialog.show();
    }

    private  void changeSpeed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayingVideoAvtivity.this);
        builder.setTitle("Set Speed");
        builder.setItems(speed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]

                if (which == 0) {

                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("0.25X");
                    PlaybackParameters param = new PlaybackParameters(0.5f);
                    player.setPlaybackParameters(param);


                }
                if (which == 1) {

                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("0.5X");
                    PlaybackParameters param = new PlaybackParameters(0.5f);
                    player.setPlaybackParameters(param);


                }
                if (which == 2) {

                    text_speed.setVisibility(View.GONE);
                    PlaybackParameters param = new PlaybackParameters(1f);
                    player.setPlaybackParameters(param);


                }
                if (which == 3) {
                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("1.5X");
                    PlaybackParameters param = new PlaybackParameters(1.5f);
                    player.setPlaybackParameters(param);

                }
                if (which == 4) {
                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("2X");
                    PlaybackParameters param = new PlaybackParameters(2f);
                    player.setPlaybackParameters(param);
                }


            }
        });
        builder.show();
    }
}