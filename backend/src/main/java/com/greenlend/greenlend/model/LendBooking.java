package com.greenlend.greenlend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "lendBooking")
public class LendBooking {
    @Id
    @GeneratedValue(generator = "lendbooking_generator")
    @SequenceGenerator(
            name = "lendbooking_generator",
            sequenceName = "lendbooking_sequence",
            initialValue = 1000
    )
    private long lendBookingId;

    @Temporal(TemporalType.DATE)
    @Column(name = "fromDate", nullable = false)
    private Date from;

    @Temporal(TemporalType.DATE)
    @Column(name = "toDate", nullable = false)
    private Date to;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false, updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GLUser borrower;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable =  false, updatable = false)
    @JsonIgnore
    private LendOffer offer;

    public void setLendBookingId(long lendBookingId) {
        this.lendBookingId = lendBookingId;
    }

    public long getLendBookingId() {
        return lendBookingId;
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

    public GLUser getBorrower() {
        return borrower;
    }

    public void setBorrower(GLUser borrower) {
        this.borrower = borrower;
    }

    public LendOffer getOffer() {
        return offer;
    }

    public void setOffer(LendOffer offer) {
        this.offer = offer;
    }
}
