package com.example.tv360.model;

import java.util.List;

public class WatchingAgainTV {

    List<InfoWatchingAgainTV> info;

    List<ScheduleModel> schedules;


    public WatchingAgainTV(List<InfoWatchingAgainTV> info, List<ScheduleModel> schedules) {
        this.info = info;
        this.schedules = schedules;
    }

    public List<InfoWatchingAgainTV> getInfo() {
        return info;
    }

    public void setInfo(List<InfoWatchingAgainTV> info) {
        this.info = info;
    }

    public List<ScheduleModel> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleModel> schedules) {
        this.schedules = schedules;
    }
}
