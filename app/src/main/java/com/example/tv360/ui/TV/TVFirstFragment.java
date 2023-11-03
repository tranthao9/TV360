package com.example.tv360.ui.TV;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;


import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tv360.R;
import com.example.tv360.TrackSelectionDialog;
import com.example.tv360.activity.PlayingVideoAvtivity;
import com.example.tv360.checkvideo.VideoCodecChecker;
import com.example.tv360.model.DataObjectUrlVideo;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.example.tv360.viewmodel.SharedTVViewModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVFirstFragment extends Fragment {

    SimpleExoPlayer playerTV;

    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";

    String userID ,profileID,accessToken,m_andoid;

    ImageView fullscreen;

    Boolean isfullscreen = false;
    HomeService apiserver ;

    String[] speed = {"0.25x", "0.5x", "Normal", "1.5x", "2x"};
    private boolean isShowingTrackSelectionDialog = false;
    private DefaultTrackSelector trackSelector;
    SharedPreferences sharedPref;

    SharedPreferences sharedPreferences_tv;
    private static  final  String SHARED_TV_PLAYING = "playingtv";

    private  static  final  String KEY_TV = "id";

    String text_quality_content = "Tự động";

    PlayerView styledPlayerViewTV;
    View view;
    Boolean ispause = false;

    TextView text_speed , text_quality;

    private SharedTVViewModel sharedtvviewmodel ;
    ImageView play;

    String id,type;
    HlsMediaSource hlsMediaSource ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_t_v_first, container, false);
        sharedtvviewmodel =  new ViewModelProvider(requireActivity()).get(SharedTVViewModel.class);
        sharedPref = getContext().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        sharedPreferences_tv = getContext().getSharedPreferences(SHARED_TV_PLAYING,MODE_PRIVATE);
        userID = sharedPref.getString(KEY_USERID,"");
        profileID = sharedPref.getString(KEY_PROFILEID,"");
        accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        m_andoid = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        sharedtvviewmodel.getSharedData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                id = strings.get(0);
                type = strings.get(1);
                if(playerTV != null)
                {
                    playerTV.stop();
                }
                PlayVideo(id,type);
            }
        });
        return view;
    }

    public void updateData(String id,String type)
    {
        if(playerTV != null)
        {
            playerTV.stop();
        }
        PlayVideo(id,type);
    }

    private void PlayVideo(String id,String type) {
        apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObjectUrlVideo> data  = apiserver.getlinka(id,type);
        data.enqueue(new Callback<DataObjectUrlVideo>() {
            @Override
            public void onResponse(Call<DataObjectUrlVideo> call, Response<DataObjectUrlVideo> response) {
                DataObjectUrlVideo urlVideo = response.body();
                assert urlVideo != null;
                if (urlVideo.getData().getUrlStreaming() == null || Objects.equals(urlVideo.getData().getUrlStreaming(), ""))
                {
                    SharedPreferences.Editor editor = sharedPreferences_tv.edit();
                    editor.putString(KEY_TV, "");
                    editor.apply();
                }
                else
                {
                    styledPlayerViewTV = view.findViewById(R.id.playvideo_tv);
                    fullscreen = styledPlayerViewTV.findViewById(R.id.exo_fullscreen_icon);

                    ImageView setiing_play = styledPlayerViewTV.findViewById(R.id.exo_settings_icon);

                     play = styledPlayerViewTV.findViewById(R.id.pause_button);
                    setiing_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openSettingPlayVideo(Gravity.BOTTOM);
                        }
                    });
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ispause == false)
                            {
                                ispause = true;
                                play.setBackgroundResource(R.drawable.baseline_play_arrow_24);
                                playerTV.setPlayWhenReady(false);
                            }
                            else
                            {
                                ispause = false;
                                play.setBackgroundResource(R.drawable.baseline_pause_24);
                                playerTV.setPlayWhenReady(true);
                            }

                        }
                    });

                    fullscreen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isfullscreen){

                                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                                if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                                    ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                                }
                                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) styledPlayerViewTV.getLayoutParams();
                                params.width = params.MATCH_PARENT;
                                params.height = (int) ( 200 * getActivity().getApplicationContext().getResources().getDisplayMetrics().density);
                                styledPlayerViewTV.setLayoutParams(params);
                                isfullscreen = false;
                            }else {

                                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                                if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                                    ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                                }

                                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) styledPlayerViewTV.getLayoutParams();
                                params.width = params.MATCH_PARENT;
                                params.height = params.MATCH_PARENT;
                                styledPlayerViewTV.setLayoutParams(params);
                                isfullscreen = true;
                            }
                        }
                    });

                    trackSelector = new DefaultTrackSelector(getContext());

                    TrackSelectionParameters newParameters = trackSelector.getParameters()
                            .buildUpon().build();

                    trackSelector.setParameters((DefaultTrackSelector.Parameters) newParameters);
                    playerTV = new SimpleExoPlayer.Builder(getContext()).setTrackSelector(trackSelector).build();
                    styledPlayerViewTV.setPlayer(playerTV);

                    hlsMediaSource = new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "exoplayer2"))).createMediaSource(Uri.parse(urlVideo.getData().getUrlStreaming()));
                    playerTV.prepare(hlsMediaSource);
                    playerTV.setPlayWhenReady(true);
                    playerTV.addAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
                            AnalyticsListener.super.onPlayerStateChanged(eventTime, playWhenReady, playbackState);
                            if(playbackState == Player.STATE_READY){
//                            progressBarTV.setVisibility(View.GONE);
                                playerTV.setPlayWhenReady(true);
                            }else if(playbackState == Player.STATE_BUFFERING){
//                            progressBarTV.setVisibility(View.VISIBLE);
                                styledPlayerViewTV.setKeepScreenOn(true);
                            }else {
//                            progressBarTV.setVisibility(View.GONE);
                                playerTV.setPlayWhenReady(true);
                            }
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<DataObjectUrlVideo> call, Throwable t) {
                call.cancel();
            }
        });
    }
    private  void  openSettingPlayVideo(int gravity)
    {
        final Dialog dialog = new Dialog(getContext());
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
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(VideoCodecChecker.isDolbyVisionSupported(getContext()),trackSelector,
                            /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);

                    trackSelectionDialog.show(getActivity().getSupportFragmentManager(), /* tag= */ null);
                    playerTV.addListener(new Player.EventListener() {
                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                            Player.EventListener.super.onTracksChanged(trackGroups, trackSelections);
                            if(trackSelections.get(0) != null)
                            {
                                Format format = trackSelections.get(0).getFormat(0);
                                if(format.codecs != null)
                                {
                                    if(format.codecs.startsWith("dvh")) text_quality_content = format.height + "p (Dolby)";
                                    else  text_quality_content = format.height + "p";
                                }
                                else text_quality_content = format.height + "p";
                            }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Set Speed");
        builder.setItems(speed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]

                if (which == 0) {

                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("0.25X");
                    PlaybackParameters param = new PlaybackParameters(0.5f);
                    playerTV.setPlaybackParameters(param);


                }
                if (which == 1) {

                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("0.5X");
                    PlaybackParameters param = new PlaybackParameters(0.5f);
                    playerTV.setPlaybackParameters(param);


                }
                if (which == 2) {

                    text_speed.setVisibility(View.GONE);
                    PlaybackParameters param = new PlaybackParameters(1f);
                    playerTV.setPlaybackParameters(param);


                }
                if (which == 3) {
                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("1.5X");
                    PlaybackParameters param = new PlaybackParameters(1.5f);
                    playerTV.setPlaybackParameters(param);

                }
                if (which == 4) {
                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("2X");
                    PlaybackParameters param = new PlaybackParameters(2f);
                    playerTV.setPlaybackParameters(param);
                }


            }
        });
        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(playerTV != null)
        {
            playerTV.stop();
        }
    }



    public void onPauseExo() {
        if(playerTV != null)
        {
            ispause = true;
            play.setBackgroundResource(R.drawable.baseline_play_arrow_24);
            playerTV.setPlayWhenReady(false);
            playerTV.stop();
        }
    }
    public void onPlayExo() {
        if(playerTV != null)
        {
            ispause = false;
            play.setBackgroundResource(R.drawable.baseline_pause_24);
            playerTV.prepare(hlsMediaSource);
            playerTV.setPlayWhenReady(true);

        }
    }


}