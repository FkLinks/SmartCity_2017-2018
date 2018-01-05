package com.henallux.smartcity.Model;

import java.io.Serializable;

public class Garden implements Serializable {
    private String name;
    private double superficie;
    private String street;
    private int numStreet;
    private String description;
    private double note;
    private String geographicalCoordinate;
    private String urlImg;
    private String urlAudio;
    //garden = new Garden(jsonGarden.getString("name"), jsonGarden.getDouble("superficie"), jsonGarden.getString("street"),
    // jsonGarden.getInt("numStreet"), jsonGarden.getString("description"),
    // jsonGarden.getDouble("note"), jsonGarden.getString("geographicalCoordinate"), jsonGarden.getString("urlImg"),jsonGarden.getString("urlAudio"));
    public Garden(String name, double superficie, String street, int numStreet, String description, double note, String geographicalCoordinate, String urlImg, String urlAudio) {
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

    public double getNote() {
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

    public void setNote(double note) {
        this.note = note;
    }

    public void setGeographicalCoordinate(String geographicalCoordinate) {
        this.geographicalCoordinate = geographicalCoordinate;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }
}
