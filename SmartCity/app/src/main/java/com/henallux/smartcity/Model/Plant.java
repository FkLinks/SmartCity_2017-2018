package com.henallux.smartcity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Plant implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("urlPicture")
    private String urlPicture;

    public Plant() {
    }

    public Plant(String name, String description, String urlPicture) {
        setName(name);
        setDescription(description);
        setUrlPicture(urlPicture);
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

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
