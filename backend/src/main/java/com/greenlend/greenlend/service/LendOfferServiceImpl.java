package com.greenlend.greenlend.service;

import com.google.common.annotations.VisibleForTesting;
import com.greenlend.greenlend.dto.LendOfferDTO;
import com.greenlend.greenlend.exception.NotFoundException;
import com.greenlend.greenlend.exception.OverlappingDatesException;
import com.greenlend.greenlend.exception.WrongValueException;
import com.greenlend.greenlend.model.*;
import com.greenlend.greenlend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LendOfferServiceImpl implements LendOfferService {
    private final LendOfferRepository lendOfferRepository;
    private final AvailabilityIntervalRepository availabilityIntervalRepository;
    private final LendBookingRepository lendBookingRepository;
    private final OfferPhotoRepository offerPhotoRepository;
    private final GLUserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public LendOfferServiceImpl(LendOfferRepository lendOfferRepository, AvailabilityIntervalRepository availabilityIntervalRepository, LendBookingRepository lendBookingRepository, OfferPhotoRepository offerPhotoRepository, GLUserRepository userRepository, ChatRepository chatRepository) {
        this.lendOfferRepository = lendOfferRepository;
        this.availabilityIntervalRepository = availabilityIntervalRepository;
        this.lendBookingRepository = lendBookingRepository;
        this.offerPhotoRepository = offerPhotoRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public LendOffer findLendOfferById(long offerId) {
        Optional<LendOffer> offer = lendOfferRepository.findById(offerId);
        if (offer.isPresent()) {
            return offer.get();
        } else {
            throw new NotFoundException("Offer with id " + offerId + " has not been found.");
        }
    }

    @Override
    public List<LendOffer> findAllLendOffers() {
        return lendOfferRepository.findAll();
    }

    @Override
    public List<LendOffer> findLatest(Integer offset, Integer perPage) {
        Pageable pageAndCount = PageRequest.of(offset, perPage, Sort.by("createdAt").descending());
        return lendOfferRepository.findByActive(true, pageAndCount);
    }

    @Override
    public List<LendOffer> findNearest(Integer offset, Integer perPage, Double lat, Double lon) {
        List<LendOffer> list = lendOfferRepository.findByActive(true);
        Collections.sort(list, new Comparator<LendOffer>() {
            @Override
            public int compare(LendOffer o1, LendOffer o2) {
                double o1Distance = o1.calculateDistanceFrom(lat, lon);
                double o2Distance = o2.calculateDistanceFrom(lat, lon);
                return Double.compare(o1Distance, o2Distance);
            }
        });
        PagedListHolder<LendOffer> page = new PagedListHolder(list);
        page.setPageSize(perPage);
        page.setPage(offset);
        return page.getPageList();

    }

    @Override
    public List<LendOffer> findLendOffersByParameters(String searchQuery, Integer min_price, Integer max_price, String city, Long[] shouldHaveCategories, Integer maxresults, Integer offset, Integer perPage) {
        List<LendOffer> list = lendOfferRepository.findByActive(true);
        if (searchQuery != null) {
            if (!searchQuery.isEmpty()) {
                list = list.stream()
                        .filter(item ->
                                item.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(searchQuery.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        if (min_price != null) {
            list.removeIf(e -> e.getPrice() < min_price);
        }
        if (max_price != null) {
            list.removeIf(e -> e.getPrice() > max_price);
        }
        if (city != null) {
            list.removeIf(e -> !e.getOfferAddress().getCity().equalsIgnoreCase(city));
        }
        if (shouldHaveCategories != null) {
            list.removeIf(e ->
            {
                List<Long> haveCategories = e.getCategories().stream().map(Category::getCategoryId).collect(Collectors.toList());
                return !haveCategories.containsAll(Arrays.asList(shouldHaveCategories));
            });
        }
        list = list.subList(0, maxresults > list.size() ? list.size() : maxresults);
        PagedListHolder<LendOffer> page = new PagedListHolder<>(list);
        page.setPageSize(perPage);
        page.setPage(offset);
        return page.getPageList();
    }

    @Override
    public LendOffer createOffer(LendOffer newOffer) {
        checkOfferValidity(newOffer);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newOffer.setOwner(userRepository.findByUsername(username));
        newOffer.setCreatedAt(new Date());

        return lendOfferRepository.save(newOffer);
    }

    @Override
    @PreAuthorize("#owner == authentication.principal.username")
    public LendOffer replaceOffer(LendOffer newOffer, Long offerId, String owner) {
        checkOfferValidity(newOffer);

        return lendOfferRepository.findById(offerId)
                .map(oldOffer -> {
                    oldOffer.setName(newOffer.getName());
                    oldOffer.setDescription(newOffer.getDescription());
                    oldOffer.setPrice(newOffer.getPrice());
                    oldOffer.setPhoto(newOffer.getPhoto());
                    oldOffer.setOfferAddress(newOffer.getOfferAddress());
                    return lendOfferRepository.save(oldOffer);
                }).orElseGet(() -> {
                    newOffer.setLendOfferId(offerId);
                    return lendOfferRepository.save(newOffer);
                });
    }

    @Override
    public void deleteOfferById(Long offerId) {
        lendOfferRepository.deleteById(offerId);
    }

    // Obsolete
    @Override
    @PreAuthorize("#owner == authentication.principal.username")
    public void addAvailableInterval(Long offerId, AvailabilityInterval availabilityInterval, String owner) {
        LendOffer lendOffer = findLendOfferById(offerId);
        availabilityIntervalRepository.save(availabilityInterval);
        lendOffer.addAvailableInterval(availabilityInterval);
        lendOfferRepository.save(lendOffer);
    }

    @Override
    public void addBooking(Long offerId, LendBooking booking) {
        LendOffer lendOffer = findLendOfferById(offerId);
        if (booking.getFrom() == null) {
            throw new WrongValueException("Zadejte počáteční datum rezervace");
        }
        if (booking.getTo() == null) {
            throw new WrongValueException("Zadejte koncové datum rezervace");
        }
        if (isInThePast(booking)) {
            throw new WrongValueException("Nemůžeme vytvořit rezervaci v minulosti");
        }
        if (isAvailable(lendOffer, booking)) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            GLUser user = userRepository.findByUsername(username);

            if (lendOffer.getOwner().getUsername().equals(user.getUsername())) {
                throw new WrongValueException("Jako vlastník nemůžete reagovat na vlastní nabídku.");
            }

            booking.setBorrower(user);
            lendOffer.addBooking(booking);
            lendBookingRepository.save(booking);
            lendOfferRepository.save(lendOffer);
            // If first booking of this offer, create chat thread
            if(chatRepository.getChatByOfferAndInterested(lendOffer, user) == null) {
                Chat thread = new Chat();
                thread.setOffer(lendOffer);
                thread.setInterested(user);
                chatRepository.save(thread);
            }
        } else {
            throw new OverlappingDatesException("Datum vaší rezervace se překrývá s jinou rezervací");
        }
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Optional<LendBooking> bookingOpt = lendBookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            LendBooking booking = bookingOpt.get();
            booking.getOffer().removeBooking(booking);
            lendBookingRepository.delete(booking);
        }
    }

    @Override
    public void addPhoto(Long offerId, OfferPhoto photo) {
        LendOffer lendOffer = findLendOfferById(offerId);
        offerPhotoRepository.save(photo);
        lendOffer.setPhoto(photo);
        lendOfferRepository.save(lendOffer);
    }

    @Override
    public void deletePhotoFromOffer(Long offerId) {
        LendOffer lendOffer = findLendOfferById(offerId);
        OfferPhoto photo = lendOffer.getPhoto();
        if (photo != null) {
            offerPhotoRepository.delete(lendOffer.getPhoto());
            lendOffer.setPhoto(null);
            lendOfferRepository.save(lendOffer);
        }
    }

    @Override
    public List<LendBooking> getBookings(Long offerId) {
        return findLendOfferById(offerId).getBookings();
    }

    @Override
    public List<LendBooking> getRelevantBookings(Long offerId) {
        LendOffer offer = findLendOfferById(offerId);
        return lendBookingRepository.findByFromAfterAndOffer(yesterday(), offer);
    }

    @VisibleForTesting
    boolean isAvailable(LendOffer offer, LendBooking booking) {
        Date from = booking.getFrom();
        Date to = booking.getTo();

        for (LendBooking otherBooking : offer.getBookings()) {
            if (from.before(otherBooking.getTo()) && to.after(otherBooking.getFrom())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public LendOfferDTO convertLendOfferToDTO(LendOffer offer) {
        return new LendOfferDTO(offer);
    }

    @Override
    public LendOfferDTO convertLendOfferToDTO(long offerId) {
        LendOffer offer = findLendOfferById(offerId);
        if (offer != null) {
            return new LendOfferDTO(offer);
        } else {
            throw new NotFoundException("No lend offer matches the id.");
        }
    }

    @Override
    public List<LendOfferDTO> convertLendOfferListToDTOs(List<LendOffer> lendOffers) {
        return lendOffers.stream().map(LendOfferDTO::new).collect(Collectors.toList());
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private boolean addressIsValid(Address address) {
        return (address.getCity() != null && address.getStreet() != null && address.getZipcode() != null);
    }

    private boolean isInThePast(LendBooking booking) {
        Date now = new Date(System.currentTimeMillis() - 36000 * 1000);
        return (booking.getFrom().before(now));
    }

    private void checkOfferValidity(LendOffer offer) {
        if (offer.getName() == null || offer.getName().equals("")) {
            throw new WrongValueException("Vyplňte jméno");
        }
        if (offer.getPrice() == null || offer.getPrice() < 0) {
            throw new WrongValueException("Cena musí být větší nebo rovna nule");
        }
        if (offer.getDescription() == null || offer.getDescription().equals("")) {
            throw new WrongValueException("Vyplňte popis");
        }
        if (offer.getCategories().size() == 0) {
            throw new WrongValueException("Vyberte alespoň jednu kategorii");
        }
        if (offer.getOfferAddress() == null) {
            GLUser owner = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (owner.getUserAddress() != null) {
                offer.setOfferAddress(owner.getUserAddress());
                if (!addressIsValid(offer.getOfferAddress())) {
                    throw new WrongValueException("Adresa uživatele není kompletní");
                }
            }
            else
                throw new WrongValueException("Nemáte vyplněnou adresu");
        }
        else if (!addressIsValid(offer.getOfferAddress())) {
            throw new WrongValueException("Adresa není kompletní");
        }
    }
}
