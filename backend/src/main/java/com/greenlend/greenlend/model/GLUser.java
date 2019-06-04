package com.greenlend.greenlend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "lguser")
@JsonIgnoreProperties(value = "bookings")
public class GLUser {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 1000
    )
    private long userId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone;

    @OneToOne
    @JoinColumn(name = "userAddress")
    private Address userAddress;

    @OneToMany(
            mappedBy = "borrower",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LendBooking> bookings = new ArrayList<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<LendOffer> lendOffers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.GUEST;

    public long getUserId() {
        return userId;
    }

    public List<LendOffer> getLendOffers() {
        return lendOffers;
    }

    public void setLendOffers(List<LendOffer> lendOffers) {
        this.lendOffers = lendOffers;
    }

    public String getFirstname() {
        return firstname;
    }

    public void erasePassword() {
        this.password = null;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<LendBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<LendBooking> bookings) {
        this.bookings.clear();
        this.bookings.addAll(bookings);
    }

    public Address getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Address userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLUser GLUser = (GLUser) o;
        return userId == GLUser.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
