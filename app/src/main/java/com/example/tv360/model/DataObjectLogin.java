package com.example.tv360.model;

public class DataObjectLogin {
    private UserModel data;
    private  String message;

    private  int errorCode;

    public DataObjectLogin(UserModel data, String message, int errorCode) {
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
