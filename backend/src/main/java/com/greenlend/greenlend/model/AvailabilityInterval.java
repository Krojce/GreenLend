package com.greenlend.greenlend.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "availabilityInterval")
public class AvailabilityInterval {
    @Id
    @GeneratedValue(generator = "availability_generator")
    @SequenceGenerator(
            name = "availability_generator",
            sequenceName = "availability_sequence",
            initialValue = 1000
    )
    private long availabilityId;

    @Temporal(TemporalType.DATE)
    @Column(name = "fromDate")
    private Date from;

    @Temporal(TemporalType.DATE)
    @Column(name = "toDate")
    private Date to;

//    @ManyToOne
//    @JoinColumn(name = "interval_id")
//    private LendOffer offer;


    public long getAvailabilityId() {
        return availabilityId;
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

//    public LendOffer getOffer() {
//        return offer;
//    }
//
//    public void setOffer(LendOffer offer) {
//        this.offer = offer;
//    }
}
