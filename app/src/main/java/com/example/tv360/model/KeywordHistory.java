package com.example.tv360.model;

import com.google.gson.annotations.SerializedName;

public class KeywordHistory {
    @SerializedName("keyword")
    private String keyword;

    public KeywordHistory(String keyword) {
        this.keyword = keyword;
    }
}
