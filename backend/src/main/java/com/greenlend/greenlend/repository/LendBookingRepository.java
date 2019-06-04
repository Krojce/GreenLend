package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.model.LendOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LendBookingRepository extends JpaRepository<LendBooking, Long> {
    List<LendBooking> findByFromAfterAndOffer(Date date, LendOffer offer);
    LendBooking findFirstByFromAfterAndOfferOrderByFrom(Date date, LendOffer offer);
}
