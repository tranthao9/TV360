package com.example.tv360.model;

import java.util.List;

public class DataObjectSearchContent {
    private  String message;

    private List<SearchContentModel> data;

    public DataObjectSearchContent(String message, List<SearchContentModel> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SearchContentModel> getData() {
        return data;
    }

    public void setData(List<SearchContentModel> data) {
        this.data = data;
    }
}
