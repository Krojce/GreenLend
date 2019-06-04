package com.greenlend.greenlend.service;

import com.greenlend.greenlend.dto.LendOfferDTO;
import com.greenlend.greenlend.model.AvailabilityInterval;
import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.model.LendOffer;
import com.greenlend.greenlend.model.OfferPhoto;

import java.util.List;

public interface LendOfferService {
    LendOffer findLendOfferById(long offerId);

    List<LendOffer> findAllLendOffers();

    List<LendOffer> findLatest(Integer offset, Integer perPage);

    List<LendOffer> findNearest(Integer offset, Integer perPage, Double lat, Double lon);

    List<LendOffer> findLendOffersByParameters(String name, Integer min_price, Integer max_price, String city, Long[] category, Integer maxresults, Integer offset, Integer perPage);

    LendOffer createOffer(LendOffer newOffer);

    LendOffer replaceOffer(LendOffer newOffer, Long offerId, String owner);

    void deleteOfferById(Long offerId);

    void addAvailableInterval(Long offerId, AvailabilityInterval availabilityInterval, String owner);

    void addBooking(Long offerId, LendBooking booking);

    void deleteBooking(Long bookingId);

    List<LendBooking> getBookings(Long offerId);

    List<LendBooking> getRelevantBookings(Long offerId);

    void addPhoto(Long offerId, OfferPhoto photo);

//    void addHires(Long offerId, byte[] hires);
//
//    void addThumbnail(Long offerId, byte[] thumbnail);
//
//    byte[] getOfferHires(Long offerId);
//
//    byte[] getOfferThumbnail(Long offerId);

    void deletePhotoFromOffer(Long offerId);

    LendOfferDTO convertLendOfferToDTO(LendOffer offer);

    LendOfferDTO convertLendOfferToDTO(long offerId);

    List<LendOfferDTO> convertLendOfferListToDTOs(List<LendOffer> lendOffers);
}
