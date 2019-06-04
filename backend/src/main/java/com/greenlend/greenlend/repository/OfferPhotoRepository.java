package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.OfferPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferPhotoRepository extends JpaRepository<OfferPhoto, Long> {
}
