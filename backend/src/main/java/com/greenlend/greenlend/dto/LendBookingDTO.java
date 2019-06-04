package com.greenlend.greenlend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.utils.DateConvertor;

import java.util.Date;

public class LendBookingDTO {
//    Response: {from, to, name, price, user{firstname, lastname}
    private Date from;
    private Date to;
    private int price;
    private String name; // Lend Offer name
    @JsonProperty("borrower")
    private OwnerDTO borrower; // Reusing the one and same DTO
    private long chatId;

    public LendBookingDTO() {

    }

    public LendBookingDTO(LendBooking booking) {
        this.from = booking.getFrom();
        this.to = booking.getTo();
        this.price = ((int)DateConvertor.daysBetween(from, to)+1) * booking.getOffer().getPrice();
        this.name = booking.getOffer().getName();

        // Sometimes we do not want to send borrowers, e.g. when some random mofo aks for all bookings but is not admin
        // or owner
        if (booking.getBorrower() != null) {
            this.borrower = new OwnerDTO(booking.getBorrower());
        }
    }

    public Date getFrom() {
        return from;
    }
    public void setFrom(Date from) {
        this.from = from;
    }
    public Date getTo() {
        return to;
    }
    public void setTo(Date to) {
        this.to = to;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public OwnerDTO getBorrower() {
        return borrower;
    }
    public void setBorrower(OwnerDTO borrower) {
        this.borrower = borrower;
    }
    public long getChatId() {
        return chatId;
    }
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
