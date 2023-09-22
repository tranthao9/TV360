package com.example.tv360.model;

public class ScheduleModel {
    private  String id;
    private String name;
    private String description;
    private String liveId;
    private int isReplay;
    private String startTime;
    private  String endTime;
    private  int status;
    private  int positionPercent;
    private  String datetime;
    private  String date;
    private int duration;

    public ScheduleModel(String id, String name, String description, String liveId, int isReplay, String startTime, String endTime, int status, int positionPercent, String datetime, String date, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.liveId = liveId;
        this.isReplay = isReplay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.positionPercent = positionPercent;
        this.datetime = datetime;
        this.date = date;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public int getIsReplay() {
        return isReplay;
    }

    public void setIsReplay(int isReplay) {
        this.isReplay = isReplay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPositionPercent() {
        return positionPercent;
    }

    public void setPositionPercent(int positionPercent) {
        this.positionPercent = positionPercent;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
