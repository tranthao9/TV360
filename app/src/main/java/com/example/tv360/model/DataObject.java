package com.example.tv360.model;

import java.util.List;

public class DataObject {
    private  String message;

    private List<HomeModel> data;

    public DataObject(String message, List<HomeModel> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeModel> getData() {
        return data;
    }

    public void setData(List<HomeModel> data) {
        this.data = data;
    }
}
