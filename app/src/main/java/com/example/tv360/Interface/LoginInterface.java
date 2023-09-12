package com.example.tv360.Interface;

public interface LoginInterface {
    void  loginSuccess(String accessToken, String refreshToken, String userId, String profileId);
    void  loginError();
}
