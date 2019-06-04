package com.greenlend.greenlend.dto;

import com.greenlend.greenlend.model.Address;

public class AddressDTO {

    private long id;

    private String city;
    private String street;
    private String zipcode;

    private double longitude;
    private double latitude;

    public AddressDTO() {}

    public AddressDTO(Address address) {
        if (address == null) {
            return;
        }
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipcode = address.getZipcode();
        this.id = address.getAddressId();
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
