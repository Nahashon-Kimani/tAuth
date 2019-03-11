package com.e.tauth.model;

public class EventName {
    private String eventDate, eventDescription, eventImageUrl, vipPrice, economyPrice;

    public EventName() {
    }

    public EventName(String eventDate, String eventDescription, String eventImageUrl, String vipPrice, String economyPrice) {
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.eventImageUrl = eventImageUrl;
        this.vipPrice = vipPrice;
        this.economyPrice = economyPrice;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(String economyPrice) {
        this.economyPrice = economyPrice;
    }
}
