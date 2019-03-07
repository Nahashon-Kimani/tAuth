package com.e.tauth.model;

public class UserRegiatration {
    private String userClass, user, regDate;
    private int noOfTickets, payableAmount;

    public UserRegiatration() {
    }

    public UserRegiatration(String userClass, String user, String regDate, int noOfTickets, int payableAmount) {
        this.userClass = userClass;
        this.user = user;
        this.regDate = regDate;
        this.noOfTickets = noOfTickets;
        this.payableAmount = payableAmount;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public int getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(int payableAmount) {
        this.payableAmount = payableAmount;
    }
}