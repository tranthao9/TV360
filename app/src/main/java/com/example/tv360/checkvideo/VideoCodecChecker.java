package com.example.tv360.checkvideo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.view.Display;

import com.google.android.exoplayer2.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class VideoCodecChecker {
    public  static  boolean issupportdolby() {
        boolean dlbAC4IMSDevice = false;

        for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
            MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
            if (info.isEncoder()) {
                continue;
            }
            for (String type : info.getSupportedTypes()) {
                if (type.equals("audio/ac4")) { //ac4
                    dlbAC4IMSDevice = true;
                }
            }
        }
        return dlbAC4IMSDevice;
    }

    public static boolean isDolbyVisionSupported(Context context)
    {
        Display screen = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            screen = context.getDisplay();
        }
        if(screen == null) return  false;
        Display.HdrCapabilities capabilities = screen.getHdrCapabilities();
        return !(Arrays.stream(capabilities.getSupportedHdrTypes()).filter(x -> x == Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION).boxed().collect(Collectors.toCollection(ArrayList::new)).isEmpty());
    }

    public  static  int GetConfiguration(Context context)
    {
        if(issupportdolby() && isDolbyVisionSupported(context)) return  2;
        else
        {
            if(isDolbyVisionSupported(context)) return 0;
            else if (issupportdolby()) return  1;
            else return  -1;
        }
    }
}
