package com.example.tv360.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.util.Pair;
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
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayingVideoAvtivity extends AppCompatActivity{

    SimpleExoPlayer player;
    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    String[] speed = {"0.25x", "0.5x", "Normal", "1.5x", "2x"};
    private boolean isShowingTrackSelectionDialog = false;
    private DefaultTrackSelector trackSelector;

    String text_quality_content = "Tự động";

    ImageView fullscreen;

    Boolean isfullscreen = false;
    HomeService apiserver;

    ProgressBar progressBar;

    Boolean ispause = false;

    TextView text_speed;

    TextView text_quality;

    PlayerView styledPlayerView;
    HlsMediaSource hlsMediaSource ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_playing_video_avtivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        styledPlayerView = findViewById(R.id.playvideo);
//        http://local-a.tivi360.vn:30002/public/v1/composite/get-link?id=19394&type=vod&t=1698824659
        apiserver = ApiService.getlinknocontenttype(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
//        Call<DataObjectUrlVideo> data  = apiserver.getlinkvod("19394","vod","1698824659");
        String id = getIntent().getStringExtra("id");
        Call<DataObjectUrlVideo> data  = apiserver.getlinka(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"));
        data.enqueue(new Callback<DataObjectUrlVideo>() {
            @Override
            public void onResponse(Call<DataObjectUrlVideo> call, Response<DataObjectUrlVideo> response) {
                DataObjectUrlVideo urlVideo = response.body();
                fullscreen = styledPlayerView.findViewById(R.id.exo_fullscreen_icon);
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
                        }
                        else
                        {
                            ispause = false;
                            play.setBackgroundResource(R.drawable.baseline_pause_24);
                            player.setPlayWhenReady(true);
                        }
                    }
                });
                fullscreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isfullscreen){

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

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
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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
                trackSelector = new DefaultTrackSelector();
                trackSelector.setParameters(
                        trackSelector.getParameters().buildUpon()
                                .build());

                player = new SimpleExoPlayer.Builder(PlayingVideoAvtivity.this).setTrackSelector(trackSelector).build();
                styledPlayerView.setPlayer(player);
                if(VideoCodecChecker.issupportdolby() && Objects.equals(getIntent().getStringExtra("id"), "4658"))
                {
                    //when playing with device support dolby.
                    DefaultHttpDataSourceFactory mediaDataSourceFactory = new DefaultHttpDataSourceFactory(
                            Util.getUserAgent(PlayingVideoAvtivity.this, "tv360"));
                    // do not meter bandwidth for manifest loading
                    DefaultHttpDataSourceFactory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(
                            Util.getUserAgent(PlayingVideoAvtivity.this, "tv360"));
                    // create the media source for DASH
                    MediaSource mediaSource = new DashMediaSource.Factory(
                            new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                            manifestDataSourceFactory)
                            .createMediaSource((Uri.parse("http://cdn-vttvas.s3.cloudstorage.com.vn/video1/dv/output/stream.mpd")) , null, null);

                    player.prepare(mediaSource);
                }
                else
                {
                    Log.d("sourceUrl ", ""+urlVideo);
                    hlsMediaSource = new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(PlayingVideoAvtivity.this, "exoplayer"))).createMediaSource(Uri.parse(urlVideo.getData().getUrlStreaming()));
                    player.prepare(hlsMediaSource);
                }
                player.setPlayWhenReady(true);
//
                player.addAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
                        AnalyticsListener.super.onPlayerStateChanged(eventTime, playWhenReady, playbackState);
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

    private MediaSource buildMediaSource(Uri uri) {

        String userAgent = Util.getUserAgent(this, getString(R.string.app_name));
        if (uri.getLastPathSegment().contains("m3u8")) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else {
            //when playing with device support dolby.
            DefaultHttpDataSourceFactory mediaDataSourceFactory = new DefaultHttpDataSourceFactory(
                    Util.getUserAgent(PlayingVideoAvtivity.this, "tv360"));
            // do not meter bandwidth for manifest loading
            DefaultHttpDataSourceFactory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(
                    Util.getUserAgent(PlayingVideoAvtivity.this, "tv360"));
            // create the media source for DASH
            MediaSource mediaSource = new DashMediaSource.Factory(
                    new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                    manifestDataSourceFactory)
                    .createMediaSource(Uri.parse("http://cdn-vttvas.s3.cloudstorage.com.vn/video1/dv/output/stream_4.mpd"), null, null);
            return mediaSource;
        }
    }
    @Override
    protected void  onStop()
    {
        super.onStop();
        styledPlayerView.setPlayer(null);
        player.setPlayWhenReady(false);
        player.release();
        TrackSelectionDialog.returnTrack();

    }

    private  void  openSettingPlayVideo(int gravity)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayingVideoAvtivity.this);
        builder.setTitle("Set Speed");
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
        text_quality.setText(text_quality_content);

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
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(VideoCodecChecker.isDolbyVisionSupported(PlayingVideoAvtivity.this),trackSelector,
                            /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
                    player.addAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                            AnalyticsListener.super.onTracksChanged(eventTime, trackGroups, trackSelections);
                            Format format = trackSelections.get(0).getFormat(0);
                            if(format.codecs != null)
                            {
                                if(format.codecs.startsWith("dvh")) text_quality_content = format.height + "p (Dolby)";
                                else  text_quality_content = format.height + "p";
                            }
                            else text_quality_content = format.height + "p";
                        }
                    });
                    dialog.dismiss();

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