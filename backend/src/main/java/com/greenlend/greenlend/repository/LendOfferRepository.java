package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.LendOffer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendOfferRepository extends JpaRepository<LendOffer, Long> {
    List<LendOffer> findByActive(boolean active, Pageable pageable);
    List<LendOffer> findByActive(boolean active);
}
