package com.henallux.smartcity.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("startTime")
    private String startTime;

    @Nullable
    @SerializedName("endTime")
    private String endTime;

    @Nullable
    @SerializedName("url")
    private String url;

    public Event() {
    }

    public Event(String name, String description, String startTime, String endTime, String url) {
        setName(name);
        setDescription(description);
        setStartTime(startTime);
        setEndTime(endTime);
        setUrl(url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Nullable
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable String endTime) {
        this.endTime = endTime;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }
}
