package com.greenlend.greenlend.service;

import com.greenlend.greenlend.dto.LendBookingDTO;
import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.model.LendOffer;
import com.greenlend.greenlend.repository.ChatRepository;
import com.greenlend.greenlend.repository.LendBookingRepository;
import com.greenlend.greenlend.utils.DateConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LendBookingServiceImpl implements LendBookingService {

    @Autowired
    ChatRepository chatdao;

    @Autowired
    LendBookingRepository bookingdao;

    @Override
    public LendBookingDTO convertLendBookingToDTO(LendBooking booking) {
        LendBookingDTO lbdto = new LendBookingDTO(booking);

        if(booking.getBorrower() != null) {
            long chatId = chatdao.getChatByOffer_LendOfferIdAndInterested(booking.getOffer().getLendOfferId(), booking.getBorrower()).getChatId();
            lbdto.setChatId(chatId);
        }

        return lbdto;
    }

    @Override
    public List<LendBookingDTO> convertLendBookingListToDTOs(List<LendBooking> bookings) {
        return bookings.stream().map(this::convertLendBookingToDTO).collect(Collectors.toList());
    }

    @Override
    public LendBooking findNextBooking(LendOffer offer) {
        return bookingdao.findFirstByFromAfterAndOfferOrderByFrom(DateConvertor.yesterday(), offer);
    }
}
