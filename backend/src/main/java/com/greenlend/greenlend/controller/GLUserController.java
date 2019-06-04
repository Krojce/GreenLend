package com.greenlend.greenlend.controller;

import com.greenlend.greenlend.dto.AddressDTO;
import com.greenlend.greenlend.dto.LendBookingDTO;
import com.greenlend.greenlend.dto.LendOfferDTO;
import com.greenlend.greenlend.dto.UserDTO;
import com.greenlend.greenlend.exception.UserAlreadyExistAuthenticationException;
import com.greenlend.greenlend.model.Address;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.model.LendBooking;
import com.greenlend.greenlend.service.ChatService;
import com.greenlend.greenlend.service.GLUserService;
import com.greenlend.greenlend.service.LendBookingService;
import com.greenlend.greenlend.utils.DateConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class GLUserController {
    private final GLUserService GLUserService;
    private final LendBookingService LendBookingService;
    private final ChatService ChatService;

    @Autowired
    public GLUserController(GLUserService GLUserServicel, ChatService ChatService, LendBookingService LendBookingService) {
        this.GLUserService = GLUserServicel;
        this.ChatService = ChatService;
        this.LendBookingService = LendBookingService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> findAllUsers() {
        return GLUserService.findAllUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO findUserById(@PathVariable Long id) {
        return new UserDTO(GLUserService.findUserById(id));
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GLUser createUser(@RequestBody GLUser newUser) {
        return GLUserService.createUser(newUser);
    }

    @GetMapping("/testcreateuser")
    public GLUser createUserTest() {
        return GLUserService.createUser(new GLUser());
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id) {
        GLUserService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    public GLUser replaceUser(@RequestBody GLUser newUser, @PathVariable long id) {
        return GLUserService.replaceUser(newUser, id);
    }

    @PostMapping("/users/{id}/address")
    public UserDTO createUserAddress(@RequestBody Address address, @PathVariable long id) {
        // TODO: restrict to user and admin
        return new UserDTO(GLUserService.addAddressToUser(address, id));
    }

    @GetMapping("/users/{id}/address")
    public AddressDTO getUserAddressById(@PathVariable Long id) {
        return new AddressDTO(GLUserService.findUserById(id).getUserAddress());
    }

    @GetMapping("/users/{id}/offers")
    public List<LendOfferDTO> getUserOffersById(@PathVariable Long id) {
        return GLUserService.findUserById(id).getLendOffers().stream().map(offer -> {
            LendOfferDTO dto = new LendOfferDTO(offer);
            long revenue = offer.getBookings().stream().mapToLong(b -> DateConvertor.daysBetween(b.getFrom(), b.getTo()) * offer.getPrice()).sum();
            dto.setRevenue(revenue);
            LendBooking nextBooking = LendBookingService.findNextBooking(offer);
            if(nextBooking != null)
                dto.setNextLend(nextBooking.getFrom());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}/lends")
    public List<LendBookingDTO> getUserLendsById(@PathVariable Long id) {
        List<LendBooking> _list = new ArrayList<>();
        GLUserService.findUserById(id).getLendOffers().forEach(offer -> _list.addAll(offer.getBookings()));
        _list.sort(Comparator.comparing(LendBooking::getFrom));
//        List<LendBookingDTO> list = new ArrayList<>();
//        _list.forEach(booking -> {
//            LendBookingDTO dto = new LendBookingDTO(booking);
//            long chatId = ChatService.getChats().stream()
//                    .filter(chat -> chat.getOffer().equals(booking.getOffer()))
//                    .filter(chat -> chat.getInterested().equals(booking.getBorrower()))
//                    .map(Chat::getChatId).findFirst().get();
//            dto.setChatId(chatId);
//            list.add(dto);
//        });
//        return list;
        return LendBookingService.convertLendBookingListToDTOs(_list);
    }

    @GetMapping("/users/{id}/borrows")
    public List<LendBookingDTO> getUserBookingsById(@PathVariable Long id) {
        return LendBookingService.convertLendBookingListToDTOs(GLUserService.findUserById(id).getBookings());
    }

    @PostMapping("/registration")
    public String registration(@RequestBody GLUser userForm) {

        if (GLUserService.findByUsername(userForm.getUsername()) != null)
            throw new UserAlreadyExistAuthenticationException("Uživatelské jméno '" +
                    userForm.getUsername() + "' již někdo používá");
        if(Objects.equals(userForm.getUsername(), "") || userForm.getUsername() == null)
            throw new RuntimeException("Vyplňte uživatelské jméno");
        if(Objects.equals(userForm.getFirstname(), "") || userForm.getFirstname() == null)
            throw new RuntimeException("Vyplňte křestní jméno");
        if(Objects.equals(userForm.getLastname(), "") || userForm.getLastname() == null)
            throw new RuntimeException("Vyplňte příjmení");
        if(Objects.equals(userForm.getEmail(), "") || userForm.getEmail() == null)
            throw new RuntimeException("Vyplňte email");
        if(Objects.equals(userForm.getPassword(), "") || userForm.getPassword() == null)
            throw new RuntimeException("Vyplňte heslo");
        GLUserService.createUser(userForm);

        return "User with username " + userForm.getUsername() + " was successfully registered";
    }

    @GetMapping(value = "/whoamiauth", produces = MediaType.APPLICATION_JSON_VALUE)
    public Authentication whoami() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping(value = "/whoami", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GLUser> login() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        GLUser user = GLUserService.findByUsername(name);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.accepted().body(user);
    }
}
