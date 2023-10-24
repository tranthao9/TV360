package com.example.tv360.adapter;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Log;

public class CustomTrackSelection extends MappingTrackSelector {

    public CustomTrackSelection(Context context) {
        super();
    }

    @Override
    protected Pair<RendererConfiguration[], TrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupport) throws ExoPlaybackException {
        Log.d("selecttracks",""+rendererFormatSupports);
        return null;
    }
}
