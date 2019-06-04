package com.greenlend.greenlend.controller;


import com.greenlend.greenlend.dto.LendBookingDTO;
import com.greenlend.greenlend.dto.LendOfferDTO;
import com.greenlend.greenlend.dto.OfferPhotoDTO;
import com.greenlend.greenlend.exception.ForbiddenException;
import com.greenlend.greenlend.exception.WrongValueException;
import com.greenlend.greenlend.model.AvailabilityInterval;
import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.model.LendOffer;
import com.greenlend.greenlend.model.OfferPhoto;
import com.greenlend.greenlend.service.LendBookingService;
import com.greenlend.greenlend.service.LendOfferService;
import com.greenlend.greenlend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class LendOfferController {

    private final LendOfferService lendOfferService;
    private final LendBookingService lendBookingService;


    @Autowired
    public LendOfferController(LendOfferService lendOfferService, LendBookingService lendBookingService) {
        this.lendOfferService = lendOfferService;
        this.lendBookingService = lendBookingService;
    }

    /*
     * OFFERS
     */

    @GetMapping("/lendoffers")
    public List<LendOfferDTO> findAllLendOffers() {
        return lendOfferService.convertLendOfferListToDTOs(lendOfferService.findAllLendOffers());
    }

    @GetMapping("/lendoffers/{id}")
    public LendOfferDTO findOfferById(@PathVariable Long id) {
        return lendOfferService.convertLendOfferToDTO(lendOfferService.findLendOfferById(id));
    }

    @Secured("ROLE_USER")
    @PostMapping("/lendoffers")
    public LendOfferDTO createLendOffer(@RequestBody LendOffer newOffer) {
        return new LendOfferDTO(lendOfferService.createOffer(newOffer));
    }

    @DeleteMapping("/lendoffers/{id}")
    public void deleteOfferById(@PathVariable long id) {
        lendOfferService.deleteOfferById(id);
    }

    @Secured("ROLE_USER")
    @PutMapping("/lendoffers/{id}")
    public LendOffer replaceOffer(@RequestBody LendOffer newOffer, @PathVariable Long id) {
        String owner = lendOfferService.findLendOfferById(id).getOwner().getUsername();
        return lendOfferService.replaceOffer(newOffer, id, owner);
    }

    @Transactional
    @GetMapping("/lendoffers/latest")
    public List<LendOfferDTO> findLatestOffers(@RequestParam(name = "offset", defaultValue = "0") Integer offset, @RequestParam (name = "perpage", defaultValue = "16")Integer perpage) {
        return lendOfferService.convertLendOfferListToDTOs(lendOfferService.findLatest(offset, perpage));
    }

    @Transactional
    @GetMapping("/lendoffers/nearest")
    public List<LendOfferDTO> findNearestOffers(@RequestParam(name = "offset", defaultValue = "0") Integer offset, @RequestParam (name = "perpage", defaultValue = "16")Integer perpage,
                                             @RequestParam (name = "lat") Double lat, @RequestParam (name = "lon") Double lon) {
        return lendOfferService.convertLendOfferListToDTOs(lendOfferService.findNearest(offset, perpage, lat, lon));
    }

    @Transactional
    @GetMapping("/lendoffers/search")
    public List<LendOfferDTO> findLendOffersByParameters(@RequestParam(name = "searchQuery", required = false) String searchQuery, @RequestParam(name = "min_price", required = false) Integer min_price,
                                                      @RequestParam(name = "city", required = false) String city, @RequestParam(name = "max_price", required = false) Integer max_price,
                                                      @RequestParam(name = "category", required = false) Long[] category, @RequestParam (name = "maxresults", defaultValue = "16") Integer maxresults,
                                                      @RequestParam(name = "offset", defaultValue = "0") Integer offset, @RequestParam (name = "perpage", defaultValue = "16")Integer perpage) {
        return lendOfferService.convertLendOfferListToDTOs(lendOfferService.findLendOffersByParameters(searchQuery, min_price, max_price, city, category, maxresults, offset, perpage));
    }

    /*
     * INTERVALS
     */

    @PostMapping("/lendoffers/{id}/intervals")
    public void addAvailableInterval(@PathVariable Long id, @RequestBody AvailabilityInterval availabilityInterval) {
        String owner = lendOfferService.findLendOfferById(id).getOwner().getUsername();
        lendOfferService.addAvailableInterval(id, availabilityInterval, owner);
    }


    /*
     * BOOKINGS
     */

    @Secured("ROLE_USER")
    @PostMapping("/lendoffers/{id}/bookings")
    public LendBookingDTO addBooking(@PathVariable Long id, @RequestBody LendBooking lendBooking) {
        lendOfferService.addBooking(id, lendBooking);
        return lendBookingService.convertLendBookingToDTO(lendBooking);
    }

    @GetMapping("/lendoffers/{id}/bookings")
    public List<LendBookingDTO> getBookings(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<LendBooking> lendBookingsRelevant = lendOfferService.getRelevantBookings(id);
        if (!lendOfferService.findLendOfferById(id).getOwner().getUsername().equals(username) ||
                AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains("ROLE_ADMIN")) {
            lendBookingsRelevant.forEach(e -> e.setBorrower(null));
        }
        return lendBookingService.convertLendBookingListToDTOs(lendBookingsRelevant);
    }


    @DeleteMapping("**/bookings/{id}")
    public void deleteBookingById(@PathVariable Long id) {
        lendOfferService.deleteBooking(id);
    }


    /*
     * PHOTOS
     */

    @Secured("ROLE_USER")
    @PostMapping(value = "/lendoffers/{id}/photo")
    public void addPhotos(@PathVariable Long id, @RequestBody String hires, Authentication auth) {
        if (hires == null || hires.equals("")) {
            throw new WrongValueException("'hires' must not be null!");
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        LendOffer lendOffer = lendOfferService.findLendOfferById(id);
        UserDetails userDetails = ((UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));

        if (roles.contains("ROLE_ADMIN") || userDetails.getUsername().equals(lendOffer.getOwner().getUsername())) {
            OfferPhoto photo = new OfferPhoto();
            int indexOfDataStart = hires.indexOf(',')+1;
            photo.setMeta(hires.substring(0, indexOfDataStart));
            photo.setHires(DatatypeConverter.parseBase64Binary(hires.substring(hires.indexOf(',')+1)));
            photo.setThumbnail(ImageUtils.createThumbnailBytes(hires));
            lendOfferService.addPhoto(id, photo);
        }
        else {
            throw new ForbiddenException("You need to be the owner of the lend offer to manipulate its photo.");
        }
    }

    @GetMapping("/lendoffers/{id}/photo")
    public String getOfferPhoto(@PathVariable Long id) {
        return (new OfferPhotoDTO(lendOfferService.findLendOfferById(id).getPhoto())).getHiresBase64();
    }

    @DeleteMapping("/lendoffers/{id}/photo")
    public void deletePhotoFromOffer(@PathVariable Long id) {
        lendOfferService.deletePhotoFromOffer(id);
    }

}
