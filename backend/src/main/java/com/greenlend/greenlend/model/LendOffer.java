package com.greenlend.greenlend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(value = "bookings")
@Entity(name = "lendOffer")
public class LendOffer {
    @Id
    @GeneratedValue(generator = "lendoffer_generator")
    @SequenceGenerator(
            name = "lendoffer_generator",
            sequenceName = "lendoffer_sequence",
            initialValue = 1000
    )
    private long lendOfferId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    private Integer price;

    @OneToOne
    @Basic(fetch=FetchType.LAZY)
    private OfferPhoto photo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", nullable = false)
    private GLUser owner;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "offerAddress", nullable = false)
    private Address offerAddress;

    @ManyToOne
    @JoinColumn(name = "commenter_id")
    private LendOfferComment comment;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "offer_category",
            joinColumns = @JoinColumn(name = "lendOfferId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private List<Category> categories = new ArrayList<>();

    @OneToMany (
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "fk_offer")
    private List<AvailabilityInterval> availabilityIntervals = new ArrayList<>();

    @OneToMany (
            mappedBy = "offer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LendBooking> bookings = new ArrayList<>();


    public long getLendOfferId() {
        return lendOfferId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OfferPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(OfferPhoto photo) {
        this.photo = photo;
    }

    public GLUser getOwner() {
        return owner;
    }

    public void setOwner(GLUser owner) {
        this.owner = owner;
    }

    public Address getOfferAddress() {
        return offerAddress;
    }

    public void setOfferAddress(Address offerAddress) {
        this.offerAddress = offerAddress;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public List<AvailabilityInterval> getAvailabilityIntervals() {
        return availabilityIntervals;
    }

    public void setAvailabilityIntervals(List<AvailabilityInterval> availabilityIntervals) {
        this.availabilityIntervals.clear();
        this.availabilityIntervals.addAll(availabilityIntervals);
    }

    public List<LendBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<LendBooking> bookings) {
        this.bookings.clear();
        this.bookings.addAll(bookings);
    }

    public LendOfferComment getComment() {
        return comment;
    }

    public void setComment(LendOfferComment comment) {
        this.comment = comment;
    }

    public void setLendOfferId(long lendOfferId) {
        this.lendOfferId = lendOfferId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LendOffer lendOffer = (LendOffer) o;
        return lendOfferId == lendOffer.lendOfferId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lendOfferId);
    }

    public void addAvailableInterval(AvailabilityInterval availabilityInterval) {
        availabilityIntervals.add(availabilityInterval);
    }

    public void removeAvailableInterval(AvailabilityInterval availabilityInterval) {
        availabilityIntervals.remove(availabilityInterval);
    }

    public void addBooking(LendBooking lendBooking) {
        this.bookings.add(lendBooking);
        lendBooking.setOffer(this);
    }


    public void removeBooking(LendBooking lendBooking) {
        this.bookings.remove(lendBooking);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double calculateDistanceFrom(double lat1, double lon1) {
        double lat2 = this.getOfferAddress().getLatitude();
        double lon2 = this.getOfferAddress().getLongitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
