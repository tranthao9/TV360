package com.example.tv360.ui.TV;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.tv360.Interface.TVFragmentInterface;
import com.example.tv360.R;
import com.example.tv360.TrackSelectionDialog;
import com.example.tv360.adapter.CustomViewPagerAdapter;
import com.example.tv360.databinding.FragmentTvBinding;
import com.example.tv360.model.DataObject;
import com.example.tv360.model.DataObjectUrlVideo;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.presenter.TVFragmentPresenter;
import com.example.tv360.retrofit.ApiService;
import com.example.tv360.retrofit.HomeService;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements TVFragmentInterface {

    ExoPlayer playerTV;

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

    ProgressBar progressBarTV;

    Boolean ispause = false;

    TextView text_speed , text_quality;

    StyledPlayerView styledPlayerViewTV;

    List<FilmModel> listData = new ArrayList<>();

    List<HomeModel> listcontentFirst = new ArrayList<>();

    String userID ,profileID,accessToken,m_andoid,id ;
    SharedPreferences sharedPref;

    SharedPreferences sharedPreferences_tv;
    private static  final  String SHARED_TV_PLAYING = "playingtv";

    private  static  final  String KEY_TV = "id";

   private Boolean isScroll = false;

   private Boolean isselectedTV = true;

   private View root;

   private TabLayout tabLayout ;
   private ViewPager viewPager;

   private DraggablePanel draggablePanel;

     private   List<HomeModel> datalistTV = new ArrayList<>();

     private TVFragmentPresenter tvFragmentPresenter = new TVFragmentPresenter(this);

     private  CustomViewPagerAdapter customViewPagerAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(isselectedTV)
        {
            root = inflater.inflate(R.layout.fragment_tv, container, false);
            progressBarTV = root.findViewById(R.id.progressBar_tv2);
            draggablePanel = (DraggablePanel) root.findViewById(R.id.draggable_panel);
            draggablePanel.setFragmentManager(getActivity().getSupportFragmentManager());
            draggablePanel.setTopFragment(new TVFirstFragment());
            draggablePanel.setBottomFragment(new TVSecondFragment());
            draggablePanel.initializeView();
            tabLayout  = (TabLayout) root.findViewById(R.id.tabLayout_main);
            viewPager =(ViewPager) root.findViewById(R.id.viewlayout_pager);
            sharedPref = getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            userID = sharedPref.getString(KEY_USERID,"");
            profileID = sharedPref.getString(KEY_PROFILEID,"");
            accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
            m_andoid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            sharedPreferences_tv = getActivity().getSharedPreferences(SHARED_TV_PLAYING,MODE_PRIVATE);
            id = sharedPreferences_tv.getString(KEY_TV,"");
            isselectedTV = false;
            return  root;
        }
        return root;
    }

    public  void  updateData(String id)
    {

        playerTV.stop();
        PlayVideo(id,"LIVE");
        customViewPagerAdapter.SetData(id);
        viewPager.setAdapter(customViewPagerAdapter);

    }
    @Override
    public void getListHomeLive(List<HomeModel> list) {
        this.datalistTV = list;
        new DownloadDataTaskTV().execute();
    }

    private  class DownloadDataTaskTV extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarTV.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GetData(id);
            for (int i = 0 ; i<100; i+=10)
            {
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBarTV.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBarTV.setVisibility(View.GONE);
        }
    }

    private void GetData(String id) {
        if(Objects.equals(id, ""))
        {
            PlayVideo(datalistTV.get(0).getContentPlaying().getDetail().getId(),datalistTV.get(0).getContentPlaying().getDetail().getType());
        }
        else
        {
            PlayVideo(id,"LIVE");
        }
        Log.d("data do" , "dd " + datalistTV);
        listData = datalistTV.get(1).getContent();
        Log.d("day la 1 : " , " do la " + datalistTV);
        for (int i =2 ; i < datalistTV.size() ; i++)
        {
            if (datalistTV.get(i).getContent() != null)
            {
                listcontentFirst.add(datalistTV.get(i));
            }
        }
        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run() {
                tabLayout.addTab(tabLayout.newTab().setText("Tất cả"));
                for (int i =1 ; i<listData.size();i++)
                {
                    tabLayout.addTab(tabLayout.newTab().setText(listData.get(i).getName()));
                }
                for (int i = 0;i<tabLayout.getTabCount();i++)
                {
                    View view  = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    params.setMargins(20,0,30,20);
                    view.requestLayout();
                }
                if(Objects.equals(id, ""))
                {
                    customViewPagerAdapter = new CustomViewPagerAdapter(getContext(),listData,listcontentFirst,datalistTV.get(0).getContentPlaying().getDetail().getId());
                    viewPager.setAdapter(customViewPagerAdapter);
                }
                else
                {
                    customViewPagerAdapter = new CustomViewPagerAdapter(getContext(),listData,listcontentFirst,id);
                    viewPager.setAdapter(customViewPagerAdapter);
                }
                viewPager.setCurrentItem(0);
            }
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.selectTab(tabLayout.getTabAt(tab.getPosition()));
                if(isScroll)
                {
                    viewPager.setCurrentItem(tab.getPosition(),true);
                    isScroll = false;
                    return;
                }
                else
                {
                    viewPager.setCurrentItem(tab.getPosition(),false);
                    isScroll = false;
                    return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                isScroll = true;
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageSelected(int position) {
                isScroll = false;
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void PlayVideo(String id,String type) {
        apiserver = ApiService.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(HomeService.class);
        Call<DataObjectUrlVideo> data  = apiserver.getlinka(id,type);
        data.enqueue(new Callback<DataObjectUrlVideo>() {
            @Override
            public void onResponse(Call<DataObjectUrlVideo> call, Response<DataObjectUrlVideo> response) {
                DataObjectUrlVideo urlVideo = response.body();
                if (urlVideo.getData().getUrlStreaming() == null || urlVideo.getData().getUrlStreaming() == "")
                {
                    SharedPreferences.Editor editor = sharedPreferences_tv.edit();
                    editor.putString(KEY_TV, "");
                    editor.apply();
                }
                else
                {
                    styledPlayerViewTV = root.findViewById(R.id.playvideo_tv);
                    fullscreen = styledPlayerViewTV.findViewById(R.id.exo_fullscreen_icon);

                    ImageView setiing_play = styledPlayerViewTV.findViewById(R.id.exo_settings_icon);

                    ImageView play = styledPlayerViewTV.findViewById(R.id.pause_button);
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
                                playerTV.pause();
                            }
                            else
                            {
                                ispause = false;
                                play.setBackgroundResource(R.drawable.baseline_pause_24);
                                playerTV.setPlayWhenReady(true);
                                playerTV.play();


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
                    playerTV = new ExoPlayer.Builder(getContext()).setTrackSelector(trackSelector).build();
                    styledPlayerViewTV.setPlayer(playerTV);
                    MediaItem mediaItem = MediaItem.fromUri(urlVideo.getData().getUrlStreaming());
                    playerTV.setMediaItem(mediaItem);
                    playerTV.prepare();
                    playerTV.setPlayWhenReady(true);
                    playerTV.play();
                    playerTV.addListener(new Player.Listener() {
                        @Override
                        public void onPlaybackStateChanged(int playbackState) {
                            Player.Listener.super.onPlaybackStateChanged(playbackState);
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
                    trackSelectionDialog.show(getActivity().getSupportFragmentManager(), /* tag= */ null);

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
    public void onPause() {
        super.onPause();
        if(playerTV != null)
        {
            playerTV.stop();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(datalistTV.size() == 0)
        {
            tvFragmentPresenter.getListHomeLive();
            Log.d("Du lieu ghi lai la ", " do la "+datalistTV);
        }
        else
        {
            Log.d("Du lieu ghi lai la có", " do la "+datalistTV);
        }
    }
}