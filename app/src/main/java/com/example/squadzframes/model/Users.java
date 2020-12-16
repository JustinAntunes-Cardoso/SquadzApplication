package com.example.squadzframes.model;

public class Users {

    public String name;
    public String image;
    public String background;
    public String status;

    public Users() {
    }

    public Users(String name, String imageProfile, String imageBackground, String message) {
        this.name = name;
        this.image = imageProfile;
        this.background = imageBackground;
        this.status = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageProfile() {
        return image;
    }

    public void setImageProfile(String imageProfile) {
        this.image = imageProfile;
    }

    public String getImageBackground() {
        return background;
    }

    public void setImageBackground(String imageBackground) {
        this.background = imageBackground;
    }

    public String getMessage() {
        return status;
    }

    public void setMessage(String message) {
        this.status = message;
    }
}
