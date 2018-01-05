package com.henallux.smartcity.Model;

import java.io.Serializable;

public class Plant implements Serializable {
    private String name;
    private String description;

    public Plant() {
    }

    public Plant(String name, String description) {
        setName(name);
        setDescription(description);
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
}
