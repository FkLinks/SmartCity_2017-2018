package com.henallux.smartcity.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Garden implements Serializable {
    private String name;
    private double surfaceArea;
    private String street;
    private int numStreet;
    private String description;
    private double note;
    private String geographicalCoordinates;

    public Garden(String name, double surfaceArea, String street, int numStreet, String description, double note, String geographicalCoordinates) {
        this.name = name;
        this.surfaceArea = surfaceArea;
        this.street = street;
        this.numStreet = numStreet;
        this.description = description;
        this.note = note;
        this.geographicalCoordinates = geographicalCoordinates;
    }

    public String getName() {
        return name;
    }

    public double getSurfaceArea() {
        return surfaceArea;
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

    public String getGeographicalCoordinates() {
        return geographicalCoordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
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

    public void setGeographicalCoordinates(String geographicalCoordinates) {
        this.geographicalCoordinates = geographicalCoordinates;
    }
}
