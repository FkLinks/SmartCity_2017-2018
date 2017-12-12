package com.henallux.smartcity.Model;

import java.io.Serializable;

public class Plant implements Serializable {
    private String name;
    private String description;
    private String urlAudioGuide;

    public Plant() {
    }

    public Plant(String name, String description, String urlAudioGuide) {
        this.name = name;
        this.description = description;
        this.urlAudioGuide = urlAudioGuide;
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

    public String getUrlAudioGuide() {
        return urlAudioGuide;
    }

    public void setUrlAudioGuide(String urlAudioGuide) {
        this.urlAudioGuide = urlAudioGuide;
    }
}
