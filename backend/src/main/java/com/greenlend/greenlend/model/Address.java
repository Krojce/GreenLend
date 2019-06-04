package com.greenlend.greenlend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "address")
public class Address {
    @Id
    @GeneratedValue(generator = "address_generator")
    @SequenceGenerator(
            name = "address_generator",
            sequenceName = "address_sequence",
            initialValue = 1000
    )
    private long addressId;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String zipcode;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private int region;

    @OneToOne(mappedBy = "userAddress")
    @JsonIgnore
    private GLUser GLUser;





    public long getAddressId() {
        return addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GLUser getGLUser() {
        return GLUser;
    }

    public void setGLUser(GLUser GLUser) {
        this.GLUser = GLUser;
    }
}
