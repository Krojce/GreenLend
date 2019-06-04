package com.greenlend.greenlend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "category_generator")
    @SequenceGenerator(
            name = "category_generator",
            sequenceName = "category_sequence",
            initialValue = 1000
    )
    private long categoryId;

    @Column
    private String name;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<LendOffer> lendOffers = new ArrayList<>();


    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public List<LendOffer> getLendOffers() {
        return lendOffers;
    }

    public void setLendOffers(List<LendOffer> lendOffers) {
        this.lendOffers = lendOffers;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
