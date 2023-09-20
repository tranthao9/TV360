package com.example.tv360.model;

public class DataObjectWatchingAgainTV {
    private WatchingAgainTV data;
    private  String message;
    private  int errorCode;

    public DataObjectWatchingAgainTV(WatchingAgainTV data, String message, int errorCode) {
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public WatchingAgainTV getData() {
        return data;
    }

    public void setData(WatchingAgainTV data) {
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
