package com.example.squadzframes.model;

public class UserInformation {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String dob;


    public UserInformation(String username, String fullName, String email, String password, String dob) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }
}