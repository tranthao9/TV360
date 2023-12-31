/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tv360;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tv360.adapter.CustomTrackAdapter;
import com.example.tv360.model.TrackInfo;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dialog to select tracks.
 */
public final class TrackSelectionDialog extends DialogFragment {
    String TAG = "TRACKSELECTION";

    private final SparseArray<TrackSelectionViewFragment> tabFragments;
    private final ArrayList<Integer> tabTrackTypes;

    private static int selected = 3, getSelected = 0 , trackindex = 0;
    private DialogInterface.OnClickListener onClickListener;
    private DialogInterface.OnDismissListener onDismissListener;

    private static boolean issupportDolbyVisionP = false ;
    private static  List<TrackInfo> trackInfoList ;

    private  static  Button okButton;

    /**
     * Returns whether a track selection dialog will have content to display if initialized with the
     * specified {@link DefaultTrackSelector} in its current state.
     */
    public static boolean willHaveContent(DefaultTrackSelector trackSelector) {
        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        return mappedTrackInfo != null && willHaveContent(mappedTrackInfo);
    }

    public static void  returnTrack()
    {
        trackInfoList = null;
        getSelected = 0;
    }
    public  void  getData(int trackindexa, int selecteda,String fomat)
    {
        trackindex = trackindexa;
        selected = selecteda;
        for (TrackInfo t : trackInfoList)
        {
            t.setIsslect(t.getFormat().equals(fomat));
        }
        okButton.performClick();

    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) getResources().getDimension(R.dimen.dialog_width);
        params.height = (selected + 3) * (int) getResources().getDimension(R.dimen.dialog_height);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * Returns whether a track selection dialog will have content to display if initialized with the
     * specified {@link MappedTrackInfo}.
     */
    public static boolean willHaveContent(MappedTrackInfo mappedTrackInfo) {
        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
            if (showTabForRenderer(mappedTrackInfo, i)) {
                selected = mappedTrackInfo.getTrackGroups(i).get(0).length - 1;
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a dialog for a given {@link DefaultTrackSelector}, whose parameters will be
     * automatically updated when tracks are selected.
     *
     * @param trackSelector     The {@link DefaultTrackSelector}.
     * @param onDismissListener A {@link DialogInterface.OnDismissListener} to call when the dialog is
     *                          dismissed.
     */
    public static TrackSelectionDialog createForTrackSelector(Boolean issupportDolbyVision,
                                                              DefaultTrackSelector trackSelector, DialogInterface.OnDismissListener onDismissListener) {
        issupportDolbyVisionP = issupportDolbyVision;
        MappedTrackInfo mappedTrackInfo =
                Assertions.checkNotNull(trackSelector.getCurrentMappedTrackInfo());
        TrackSelectionDialog trackSelectionDialog = new TrackSelectionDialog();
        DefaultTrackSelector.Parameters parameters = trackSelector.getParameters();
        trackSelectionDialog.init(
                mappedTrackInfo,
                /* initialParameters = */ parameters,
                /* allowAdaptiveSelections= */ false,
                /* allowMultipleOverrides= */ false,
                /* onClickListener= */ (dialog, which) -> {
                    for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
                        if (mappedTrackInfo.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
                            trackSelector.setParameters(
                                    trackSelector.buildUponParameters()
                                            .setSelectionOverride(
                                                    i,
                                                    mappedTrackInfo.getTrackGroups(/* rendererIndex= */ i),
                                                    // this selects the first track of the second track group.
                                                    new DefaultTrackSelector.SelectionOverride(
                                                            /* groupIndex= */ trackindex, /* tracks= */selected )));
                            Toast.makeText(trackSelectionDialog.getContext(), "Bạn đang chạy với " + mappedTrackInfo.getTrackGroups(i).get(trackindex).getFormat(selected).codecs + "codec" + mappedTrackInfo.getTrackGroups(i).get(trackindex).getFormat(selected).height + "p",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                onDismissListener);
        return trackSelectionDialog;
    }

    public TrackSelectionDialog() {
        tabFragments = new SparseArray<>();
        tabTrackTypes = new ArrayList<>();
        // Retain instance across activity re-creation to prevent losing access to init data.
        setRetainInstance(true);
    }

    private void init(
            MappedTrackInfo mappedTrackInfo,
            DefaultTrackSelector.Parameters initialParameters,
            boolean allowAdaptiveSelections,
            boolean allowMultipleOverrides,
            DialogInterface.OnClickListener onClickListener,
            DialogInterface.OnDismissListener onDismissListener) {
        this.onClickListener = onClickListener;
        this.onDismissListener = onDismissListener;
        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
                if (showTabForRenderer(mappedTrackInfo, i)) {
                    int trackType = mappedTrackInfo.getRendererType(/* rendererIndex= */ i);
                    TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(i);
                    TrackSelectionViewFragment tabFragment = new TrackSelectionViewFragment();
                    tabFragment.init(
                            mappedTrackInfo,
                            /* rendererIndex= */ i,
                            initialParameters.getRendererDisabled(/* rendererIndex= */ i),
                            initialParameters.getSelectionOverride(/* rendererIndex= */ i,trackGroupArray),
                            allowAdaptiveSelections,
                            allowMultipleOverrides);
                    tabFragments.put(i, tabFragment);
                    tabTrackTypes.add(trackType);
            }
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // We need to own the view to let tab layout work correctly on all API levels. We can't use
        // AlertDialog because it owns the view itself, so we use AppCompatDialog instead, themed using
        // the AlertDialog theme overlay with force-enabled title.
        AppCompatDialog dialog =
                new AppCompatDialog(getActivity(), R.style.TrackSelectionDialogThemeOverlay);
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        onDismissListener.onDismiss(dialog);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.track_selection_dialog, container, false);
        TabLayout tabLayout = dialogView.findViewById(R.id.track_selection_dialog_tab_layout);
        ViewPager viewPager = dialogView.findViewById(R.id.track_selection_dialog_view_pager);
        Button cancelButton = dialogView.findViewById(R.id.track_selection_dialog_cancel_button);
        okButton = dialogView.findViewById(R.id.track_selection_dialog_ok_button);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setVisibility(View.GONE);
        cancelButton.setOnClickListener(view -> dismiss());
        okButton.setOnClickListener(
                view -> {
                    onClickListener.onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                    dismiss();
                });
        return dialogView;
    }

    private static boolean showTabForRenderer(MappedTrackInfo mappedTrackInfo, int rendererIndex) {
        TrackGroupArray trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex);
        if (trackGroupArray.length == 0 ) {
            return false;
        }
        int trackType = mappedTrackInfo.getRendererType(rendererIndex);
        return isSupportedTrackType(trackType);
    }

    private static boolean isSupportedTrackType(int trackType) {
        switch (trackType) {
            case C.TRACK_TYPE_VIDEO:
            case C.TRACK_TYPE_AUDIO:
            case C.TRACK_TYPE_TEXT:
                return true;
            default:
                return false;
        }
    }

    private static String getTrackTypeString(Resources resources, int trackType) {
        switch (trackType) {
            case C.TRACK_TYPE_VIDEO:
                return "VIDEO";
            case C.TRACK_TYPE_AUDIO:
                return "AUDIO";
            case C.TRACK_TYPE_TEXT:
                return "TXT";
            default:
                throw new IllegalArgumentException();
        }
    }

    private final class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }
        @Override
        @NonNull
        public Fragment getItem(int position) {
            return tabFragments.valueAt(position);
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getTrackTypeString(getResources(), tabTrackTypes.get(position));
        }
    }

    /**
     * Fragment to show a track selection in tab of the track selection dialog.
     */
    public static final class TrackSelectionViewFragment extends Fragment
            implements TrackSelectionView.TrackSelectionListener {
        private MappedTrackInfo mappedTrackInfo;
        private int rendererIndex;
        private boolean allowAdaptiveSelections;
        private boolean allowMultipleOverrides;

        /* package */ boolean isDisabled;
        /* package */ List<SelectionOverride> overrides;

        public TrackSelectionViewFragment() {
            // Retain instance across activity re-creation to prevent losing access to init data.
            setRetainInstance(true);
        }
        public void init(
                MappedTrackInfo mappedTrackInfo,
                int rendererIndex,
                boolean initialIsDisabled,
                @Nullable SelectionOverride initialOverride,
                boolean allowAdaptiveSelections,
                boolean allowMultipleOverrides) {
            this.mappedTrackInfo = mappedTrackInfo;
            this.rendererIndex = rendererIndex;
            this.isDisabled = initialIsDisabled;
            this.overrides =
                    initialOverride == null
                            ? Collections.emptyList()
                            : Collections.singletonList(initialOverride);
            this.allowAdaptiveSelections = allowAdaptiveSelections;
            this.allowMultipleOverrides = allowMultipleOverrides;
        }
        @Override
        public View onCreateView(
                LayoutInflater inflater,
                @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            View rootView =
                    inflater.inflate(
                            R.layout.exo_track_selection_dialog, container, /* attachToRoot= */ false);
            TrackGroup trackGroup;
            if(trackInfoList == null)
            {
                trackInfoList = new ArrayList<>();
                TrackInfo trackInfo = new TrackInfo("Tự động",true,0);
                trackInfoList.add(trackInfo);
                for (int i =0 ; i<mappedTrackInfo.getRendererType(rendererIndex);i++)
                {
                    try {
                            trackGroup = mappedTrackInfo.getTrackGroups(rendererIndex).get(i);
                            for (int j =0; j<mappedTrackInfo.getRendererCount();j++)
                            {
                                Format format = trackGroup.getFormat(j);
                                if(format.codecs != null)
                                {
                                    if(format.codecs.startsWith("dvh"))
                                    {
                                        if(issupportDolbyVisionP) trackInfo = new TrackInfo(trackGroup.getFormat(j).height + "p (Dolby)",false,j);
                                        else  break;
                                    }
                                    else trackInfo = new TrackInfo(trackGroup.getFormat(j).height + "p",false,j);
                                }
                                else
                                {
                                    trackInfo = new TrackInfo(trackGroup.getFormat(j).height + "p",false,j);
                                }
                                trackInfoList.add(trackInfo);
                            }
                            getSelected = i;
                    }
                    catch (Exception ex)
                    {
                        i += 1;
                    }

                }
            }
            CustomTrackAdapter customTrackAdapter = new CustomTrackAdapter(getContext(),R.layout.custom_trackselectview,trackInfoList,issupportDolbyVisionP,getSelected);
            ListView listView = rootView.findViewById(R.id.exo_track_selection_view);
            listView.setAdapter(customTrackAdapter);
            listView.setLayoutParams(new LinearLayout.LayoutParams(800,LinearLayout.LayoutParams.WRAP_CONTENT));
            return rootView;
        }

        @Override
        public void onTrackSelectionChanged(boolean isDisabled, @NonNull List<SelectionOverride> overrides) {
            this.isDisabled = isDisabled;
            this.overrides = overrides;
        }
    }
}
