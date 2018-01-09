package com.henallux.smartcity.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Garden implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("superficie")
    private double superficie;

    @SerializedName("street")
    private String street;

    @SerializedName("numStreet")
    private int numStreet;

    @SerializedName("description")
    private String description;

    @Nullable
    @SerializedName("note")
    private Double note;

    @SerializedName("geographicalCoordinate")
    private String geographicalCoordinate;

    @Nullable
    @SerializedName("urlImg")
    private String urlImg;

    @Nullable
    @SerializedName("urlAudio")
    private String urlAudio;

    public Garden(String name, double superficie, String street, int numStreet, String description, Double note, String geographicalCoordinate, String urlImg, String urlAudio) {
        setName(name);
        setSuperficie(superficie);
        setStreet(street);
        setNumStreet(numStreet);
        setDescription(description);
        setNote(note);
        setGeographicalCoordinate(geographicalCoordinate);
        setUrlImg(urlImg);
        setUrlAudio(urlAudio);
    }

    public String getName() {
        return name;
    }

    public double getSuperficie() {
        return superficie;
    }

    public String getStreet() {
        return street;
    }

    public int getNumStreet() {
        return numStreet;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public Double getNote() {
        return note;
    }

    public String getGeographicalCoordinate() {
        return geographicalCoordinate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumStreet(int numStreet) {
        this.numStreet = numStreet;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(@Nullable Double note) {
        this.note = note;
    }

    public void setGeographicalCoordinate(String geographicalCoordinate) {
        this.geographicalCoordinate = geographicalCoordinate;
    }

    @Nullable
    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(@Nullable String urlImg) {
        this.urlImg = urlImg;
    }

    @Nullable
    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(@Nullable String urlAudio) {
        this.urlAudio = urlAudio;
    }
}
