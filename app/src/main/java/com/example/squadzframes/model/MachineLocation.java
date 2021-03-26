package com.example.squadzframes.model;

public class MachineLocation {

    public String location;
    public String background;
    public String name;

    public MachineLocation() {
    }

    public MachineLocation(String location, String imageBackground, String name) {
        this.location = location;
        this.background = imageBackground;
        this.name = name;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageBackground() {
        return background;
    }

    public void setImageBackground(String imageBackground) {
        this.background = imageBackground;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

}
