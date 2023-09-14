package com.example.tv360.model;

public class DataObjectLoadMore {
    private  String errorCode;
    private  String message;
    private  HomeModel data;

    public DataObjectLoadMore(String errorCode, String message, HomeModel data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HomeModel getData() {
        return data;
    }

    public void setData(HomeModel data) {
        this.data = data;
    }
}
