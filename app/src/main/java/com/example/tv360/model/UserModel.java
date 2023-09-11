package com.example.tv360.model;

public class UserModel {
    private  String accessToken;
    private  String refreshToken;

    private  String profileId;

    public UserModel(String accessToken, String refreshToken, String profileId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.profileId = profileId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
