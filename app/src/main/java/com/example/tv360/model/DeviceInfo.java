package com.example.tv360.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceInfo  implements Serializable {
    @SerializedName("deviceDrmId")
    private String deviceDrmId;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("deviceType")
    private String deviceType;

    @SerializedName("osType")
    private String osType;

    @SerializedName("osAppType")
    private String osAppType;

    @SerializedName("osVersion")
    private  String osVersion;

    @SerializedName("deviceName")
    private  String deviceName;


    public DeviceInfo( String deviceDrmId, String deviceId, String deviceType, String osType , String osVersion , String deviceName) {
        this.deviceDrmId = deviceDrmId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.osType = osType;
        this.osVersion = osVersion;
        this.deviceName = deviceName;
    }

    public String getDeviceDrmId() {
        return deviceDrmId;
    }

    public void setDeviceDrmId(String deviceDrmId) {
        this.deviceDrmId = deviceDrmId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsAppType() {
        return osAppType;
    }

    public void setOsAppType(String osAppType) {
        this.osAppType = osAppType;
    }
}
