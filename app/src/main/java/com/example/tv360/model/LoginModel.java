package com.example.tv360.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel  implements Serializable {
    @SerializedName("msisdn")
    private  String 	msisdn;
    @SerializedName("password")
    private  String Password;
    @SerializedName("grantType")
    private  String 	grantType;
    @SerializedName("captcha")
    private  String 	captcha;
    @SerializedName("deviceInfo")
    private  DeviceInfo deviceInfo;

    public LoginModel(String msisdn, String password, String grantType, String captcha, DeviceInfo deviceInfo) {
        this.msisdn = msisdn;
        this.Password = password;
        this.grantType = grantType;
        this.captcha = captcha;
        this.deviceInfo = deviceInfo;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}

