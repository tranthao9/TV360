package com.example.tv360.model;

import java.text.Format;

public class TrackInfo {
    String format;
    boolean isslect;
    int position;

    public TrackInfo(String format, boolean isslect, int position) {
        this.format = format;
        this.isslect = isslect;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isIsslect() {
        return isslect;
    }

    public void setIsslect(boolean isslect) {
        this.isslect = isslect;
    }
}
