package com.e.tauth.model;

public class UserRegistration {
    private String username, email, phoneNo;

    public UserRegistration() {
    }

    public UserRegistration(String username, String email, String phoneNo) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
