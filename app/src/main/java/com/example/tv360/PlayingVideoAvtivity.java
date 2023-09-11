package com.example.tv360;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.media.browse.MediaBrowser;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class PlayingVideoAvtivity extends AppCompatActivity {

    ExoPlayer player;

    String UrlVideo = "https://youtu.be/_ttcR7VDouE?si=a_0kHvjqlZ6rI6Yv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_video_avtivity);

        StyledPlayerView styledPlayerView = findViewById(R.id.playvideo);
        player = new ExoPlayer.Builder(PlayingVideoAvtivity.this).build();
        styledPlayerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(UrlVideo);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void  onStop()
    {
        super.onStop();
        player.setPlayWhenReady(false);
        player.release();
        player = null;
    }
}