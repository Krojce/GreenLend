package com.greenlend.greenlend.service;

import com.greenlend.greenlend.dto.LendBookingDTO;
import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.model.LendOffer;

import java.util.List;

public interface LendBookingService {
    LendBookingDTO convertLendBookingToDTO(LendBooking booking);

    List<LendBookingDTO> convertLendBookingListToDTOs(List<LendBooking> bookings);

    LendBooking findNextBooking(LendOffer offer);
}
