package com.greenlend.greenlend.service;

import com.greenlend.greenlend.GreenlendApplicationTests;
import com.greenlend.greenlend.exception.NotFoundException;
import com.greenlend.greenlend.model.*;
import com.greenlend.greenlend.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

public class LendOfferServiceImplTest extends GreenlendApplicationTests {

    @Mock
    private ChatRepository chatRepository;
    @Mock
    private LendOfferRepository lendOfferRepository;
    @Mock
    private AvailabilityIntervalRepository availabilityIntervalRepository;
    @Mock
    private LendBookingRepository lendBookingRepository;
    @Mock
    private OfferPhotoRepository offerPhotoRepository;
    @Mock
    private GLUserRepository userRepository;

    @InjectMocks
    private LendOfferServiceImpl lendOfferService;

    private long basicId = 0L;
    private long extraId = 1L;
    private long invalidId = -1L;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // booking
        LendBooking basicBooking = new LendBooking();
        GLUser basicUser = new GLUser();
        GLUser owner = new GLUser();
        LendOffer extraOffer = new LendOffer();

        when(lendOfferRepository.save(any(LendOffer.class))).then(returnsFirstArg());
        when(lendBookingRepository.save(any(LendBooking.class))).then(returnsFirstArg());

        // user
        basicUser.setUserId(basicId);
        basicUser.setUsername("Karel");
        basicUser.setUserAddress(new Address());

        owner.setUsername("not Karel");

        when(userRepository.findByUsername("Karel")).thenReturn(basicUser);

        LendOffer basicOffer = new LendOffer();
        basicOffer.setOwner(owner);
        basicOffer.setLendOfferId(basicId);
        basicOffer.setPrice(140);
        basicOffer.setName("offer");
        basicOffer.setDescription("basic offer description");

        when(lendOfferRepository.findById(basicId)).thenReturn(java.util.Optional.of(basicOffer));

        // offer with photo
        extraOffer.setLendOfferId(extraId);
        extraOffer.setPrice(140);
        extraOffer.setName("offer with photo");
        extraOffer.setDescription("offer with photo description");
        extraOffer.addBooking(basicBooking);
        OfferPhoto photo = new OfferPhoto();
        photo.setHires(new byte[]{3});
        photo.setThumbnail(new byte[]{1});
        extraOffer.setPhoto(photo);

        when(lendOfferRepository.findById(extraId)).thenReturn(java.util.Optional.of(extraOffer));

        // booking
        basicBooking.setLendBookingId(basicId);
        basicBooking.setOffer(extraOffer);
        basicBooking.setBorrower(basicUser);
        basicBooking.setFrom(new Date(2019, Calendar.APRIL, 22));
        basicBooking.setTo(new Date(2019, Calendar.MAY, 2));

        when(lendBookingRepository.findById(basicId)).thenReturn(java.util.Optional.of(basicBooking));

        // security
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(basicUser);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(basicUser.getUsername());

    }

    @Test
    public void findLendOfferById_validId_offerFound() {
        LendOffer expectedOffer = lendOfferRepository.findById(basicId).get(); // first call for verify

        LendOffer actualOffer = lendOfferService.findLendOfferById(basicId); // second call for verify

        verify(lendOfferRepository, times(1 + 1)).findById(basicId);
        assertEquals(expectedOffer, actualOffer);
    }

    @Test
    public void findLendOfferById_invalidId_notFoundExceptionThrown() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            lendOfferService.findLendOfferById(invalidId);
        });
    }

    @Test
    public void findAllLendOffers() {
        lendOfferService.findAllLendOffers();

        verify(lendOfferRepository, times(1)).findAll();
    }

    @Test
    public void createOffer_offerIsCreated() {
        long offerId = 1L;
        String name = "Sekačka";
        String desc = "Sekačka je jako nová, ale nemá kola";
        int price = 360;

        LendOffer offer = new LendOffer();
        offer.setLendOfferId(offerId);
        offer.setName(name);
        offer.setDescription(desc);
        offer.setPrice(price);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        offer.setCategories(categories);
        Address addr = new Address();
        addr.setCity("city");
        addr.setStreet("street");
        addr.setZipcode("123");
        addr.setLatitude(123);
        addr.setLongitude(123);
        offer.setOfferAddress(addr);
        LendOffer savedOffer = lendOfferService.createOffer(offer);

        verify(lendOfferRepository, times(1)).save(offer);
        assertEquals(offerId, savedOffer.getLendOfferId());
        assertEquals(name, savedOffer.getName());
        assertEquals(desc, savedOffer.getDescription());
        assertEquals(Integer.valueOf(price), savedOffer.getPrice());
    }

    @Test
    public void replaceOffer_oldOfferFound_oldOfferSavedWithNewData() {
        LendOffer oldOffer = lendOfferRepository.findById(basicId).get();
        assertEquals("offer", oldOffer.getName());

        long offerId = 2L;
        String name = "Vrtačka";
        String desc = "Vrtačka je jako nová, ale nemá rukojeť";
        int price = 420;

        LendOffer newOffer = new LendOffer();
        newOffer.setLendOfferId(offerId);
        newOffer.setName(name);
        newOffer.setDescription(desc);
        newOffer.setPrice(price);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        newOffer.setCategories(categories);
        Address addr = new Address();
        addr.setCity("city");
        addr.setStreet("street");
        addr.setZipcode("123");
        addr.setLatitude(123);
        addr.setLongitude(123);
        newOffer.setOfferAddress(addr);
        LendOffer replacedOffer = lendOfferService.replaceOffer(newOffer, basicId, "");

        verify(lendOfferRepository, times(1)).save(oldOffer);
        verify(lendOfferRepository, times(0)).save(newOffer);
        assertEquals(name, replacedOffer.getName());
        assertEquals(desc, replacedOffer.getDescription());
        assertEquals(Integer.valueOf(price), replacedOffer.getPrice());
    }

    @Test
    public void replaceOffer_oldOfferNotFound_newOfferSaved() {
        long offerId = 2L;
        String name = "Vrtačka";
        String desc = "Vrtačka je jako nová, ale nemá rukojeť";
        int price = 420;

        LendOffer newOffer = new LendOffer();
        newOffer.setLendOfferId(offerId);
        newOffer.setName(name);
        newOffer.setDescription(desc);
        newOffer.setPrice(price);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        newOffer.setCategories(categories);
        Address addr = new Address();
        addr.setCity("city");
        addr.setStreet("street");
        addr.setZipcode("123");
        addr.setLatitude(123);
        addr.setLongitude(123);
        newOffer.setOfferAddress(addr);
        LendOffer replacedOffer = lendOfferService.replaceOffer(newOffer, invalidId, "");

        verify(lendOfferRepository, times(1)).save(newOffer);
        assertEquals(name, replacedOffer.getName());
        assertEquals(desc, replacedOffer.getDescription());
        assertEquals(Integer.valueOf(price), replacedOffer.getPrice());
    }

    @Test
    public void deleteOfferById_deleteCalled() {
        lendOfferService.deleteOfferById(basicId);

        verify(lendOfferRepository, times(1)).deleteById(basicId);
    }

    @Test
    public void addBooking_validId_saveCalled_objectsUpdated() {
        LendBooking booking = lendBookingRepository.findById(basicId).get();
        lendOfferService.addBooking(basicId, booking);
        LendOffer updatedOffer = lendOfferService.findLendOfferById(basicId);

        verify(lendBookingRepository, times(1)).save(booking);
        verify(lendOfferRepository, times(1)).save(updatedOffer);
        verify(chatRepository, times(1)).save(any(Chat.class));
        assertTrue(updatedOffer.getBookings().contains(booking));
        assertEquals(updatedOffer, booking.getOffer());
    }

    @Test
    public void deleteBooking_deleteCalled_offerUpdated() {
        // SETUP
        LendBooking booking = lendBookingRepository.findById(basicId).get();
        lendOfferService.addBooking(basicId, booking);
        LendOffer updatedOffer = lendOfferService.findLendOfferById(basicId);

        verify(lendBookingRepository, times(1)).save(booking);
        verify(lendOfferRepository, times(1)).save(updatedOffer);
        assertTrue(updatedOffer.getBookings().contains(booking));
        assertEquals(updatedOffer, booking.getOffer());

        // ACT
        lendOfferService.deleteBooking(basicId);

        // ASSERT
        verify(lendBookingRepository, times(1)).delete(booking);
        assertFalse(updatedOffer.getBookings().contains(booking));
    }

    @Test
    public void addPhoto_validOfferId_newPhotoSaved_offerSaved_offerUpdated() {
        OfferPhoto photo = new OfferPhoto();
        LendOffer offerToBeUpdated = lendOfferRepository.findById(basicId).get();
        assertNull(offerToBeUpdated.getPhoto());

        lendOfferService.addPhoto(basicId, photo);

        verify(lendOfferRepository, times(1)).save(offerToBeUpdated);
        verify(offerPhotoRepository, times(1)).save(photo);
        assertEquals(photo, offerToBeUpdated.getPhoto());
    }

    @Test
    public void addPhoto_invalidOfferId_notFoundExceptionThrown() {
        OfferPhoto photo = new OfferPhoto();

        Assertions.assertThrows(NotFoundException.class, () -> {
            lendOfferService.addPhoto(invalidId, photo);
        });
    }

    @Test
    public void deletePhotoFromOffer_validOfferId_offerHasPhoto_photoDeleted_offerUpdated() {
        LendOffer offer = lendOfferRepository.findById(extraId).get();
        OfferPhoto photoToBeDeleted = offer.getPhoto();
        assertNotNull(photoToBeDeleted);

        lendOfferService.deletePhotoFromOffer(extraId);

        verify(offerPhotoRepository, times(1)).delete(photoToBeDeleted);
        verify(lendOfferRepository, times(1)).save(offer);
        assertNull(offer.getPhoto());
    }

    @Test
    public void deletePhotoFromOffer_validOfferId_OfferHasNoPhoto_nothingHappens() {
        LendOffer offer = lendOfferRepository.findById(basicId).get();
        OfferPhoto photoToBeDeleted = offer.getPhoto();
        assertNull(photoToBeDeleted);

        lendOfferService.deletePhotoFromOffer(basicId);

        verify(offerPhotoRepository, times(0)).delete(photoToBeDeleted);
        verify(lendOfferRepository, times(0)).save(offer);
        assertNull(offer.getPhoto());
    }

    @Test
    public void deletePhotoFromOffer_invalidOfferId_notFoundExceptionThrown() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            lendOfferService.deletePhotoFromOffer(invalidId);
        });
    }

    @Test
    public void getBookings_validOfferId_offerHasBookings_bookingsReturned() {
        LendOffer offer = lendOfferRepository.findById(extraId).get();
        List<LendBooking> expectedBookings = offer.getBookings();
        assertNotNull(expectedBookings);

        List<LendBooking> actualBookings = lendOfferService.getBookings(extraId);
        verify(lendOfferRepository, times(1 + 1)).findById(extraId);
        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    public void getBookings_validOfferId_offerHasNoBookings_emptyListReturned() {
        LendOffer offer = lendOfferRepository.findById(basicId).get();
        List<LendBooking> expectedBookings = offer.getBookings();
        int expectedSize = 0;
        assertNotNull(expectedBookings);
        assertEquals(expectedSize, expectedBookings.size());

        List<LendBooking> actualBookings = lendOfferService.getBookings(basicId);
        verify(lendOfferRepository, times(1 + 1)).findById(basicId);
        assertEquals(expectedBookings, actualBookings);
        assertEquals(expectedSize, actualBookings.size());
    }

    @Test
    public void isAvailable_offerWithNoBookings_trueReturned() {
        LendOffer offer = lendOfferRepository.findById(basicId).get();
        LendBooking booking = lendBookingRepository.findById(basicId).get();

        boolean actualResult = lendOfferService.isAvailable(offer, booking);

        assertTrue(actualResult);
    }

    @Test
    public void isAvailable_bookingsInterfere_falseReturned() {
        LendOffer offer = lendOfferRepository.findById(extraId).get();
        LendBooking booking = new LendBooking();
        booking.setFrom(new Date(2019, Calendar.MAY, 1));
        booking.setTo(new Date(2019, Calendar.MAY, 1));

        boolean actualResult = lendOfferService.isAvailable(offer, booking);

        assertFalse(actualResult);
    }

    @Test
    public void isAvailable_bookingsDontInterfere_TrueReturned() {
        LendOffer offer = lendOfferRepository.findById(extraId).get();
        LendBooking booking = new LendBooking();
        booking.setFrom(new Date(2019, Calendar.MAY, 3));
        booking.setTo(new Date(2019, Calendar.MAY, 4));

        boolean actualResult = lendOfferService.isAvailable(offer, booking);

        assertTrue(actualResult);
    }
}