package com.example.tv360.model;

public class DataObjectUrlVideo {
    private String  message;
    private  String errorCode;

    private  UrlVideo data;

    public DataObjectUrlVideo(String message, String errorCode, UrlVideo data) {
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public UrlVideo getData() {
        return data;
    }

    public void setData(UrlVideo data) {
        this.data = data;
    }
}
