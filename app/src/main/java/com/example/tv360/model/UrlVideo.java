package com.example.tv360.model;

public class UrlVideo {
    private String urlStreaming;
    private String viewId;
    private String trialTime;

    public UrlVideo(String urlStreaming, String viewId, String trialTime) {
        this.urlStreaming = urlStreaming;
        this.viewId = viewId;
        this.trialTime = trialTime;
    }

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(String trialTime) {
        this.trialTime = trialTime;
    }
}
