package com.example.tv360.model;

public class InfoWatchingAgainTV {
    String name;
    String value;

    int isActive;
    int isSelected;

    public InfoWatchingAgainTV(String name, String value, int isActive, int isSelected) {
        this.name = name;
        this.value = value;
        this.isActive = isActive;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
