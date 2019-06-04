package com.greenlend.greenlend.dto;

import com.greenlend.greenlend.model.Category;
import com.greenlend.greenlend.model.LendOffer;

import java.util.Date;
import java.util.List;

public class LendOfferDTO {
    private long lendOfferId;

    private String name;
    private String description;
    private int price;
    private List<Category> categories;
    private AddressDTO address;
    private OwnerDTO owner;
    private String thumbnail;
    private Date nextLend;
    private long revenue;


    public LendOfferDTO() {}

    public LendOfferDTO(LendOffer lendOffer) {
        this.lendOfferId = lendOffer.getLendOfferId();
        this.name = lendOffer.getName();
        this.description = lendOffer.getDescription();
        this.price = lendOffer.getPrice();
        this.categories = lendOffer.getCategories();
        this.address = new AddressDTO(lendOffer.getOfferAddress());
        this.owner = new OwnerDTO(lendOffer.getOwner());
        if(lendOffer.getPhoto()!=null) {
            this.thumbnail = new OfferPhotoDTO(lendOffer.getPhoto()).getThumbnailBase64();
        }
    }

    public long getLendOfferId() {
        return lendOfferId;
    }
    public void setLendOfferId(long lendOfferId) {
        this.lendOfferId = lendOfferId;
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
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public AddressDTO getAddress() {
        return address;
    }
    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    public OwnerDTO getOwner() {
        return owner;
    }
    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getNextLend() {
        return nextLend;
    }

    public void setNextLend(Date nextLend) {
        this.nextLend = nextLend;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }
}
