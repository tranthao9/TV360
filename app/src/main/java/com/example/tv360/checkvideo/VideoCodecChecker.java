package com.example.tv360.checkvideo;

import android.content.Context;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;

import java.io.IOException;


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
