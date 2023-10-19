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
            screen = screen = context.getDisplay();
        }
        Log.d("screen dolby vision",""+screen);
        Display.HdrCapabilities capabilities = screen.getHdrCapabilities();
        return !(Arrays.stream(capabilities.getSupportedHdrTypes()).filter(x -> x == Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION).boxed().collect(Collectors.toCollection(ArrayList::new)).isEmpty());
    }
    public  static  String getVideoc(String path)
    {
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(path);
            int a = mediaExtractor.getTrackCount();
            for (int i = 0; i< a;i++)
            {
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                if(mime.startsWith("video/"))
                {
                    return  mime;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            mediaExtractor.release();
        }
        return  "";
    }
}
