package com.greenlend.greenlend.service;

import com.greenlend.greenlend.exception.ForbiddenException;
import com.greenlend.greenlend.exception.NotFoundException;
import com.greenlend.greenlend.exception.UserAlreadyExistAuthenticationException;
import com.greenlend.greenlend.exception.WrongValueException;
import com.greenlend.greenlend.model.Address;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.model.UserRole;
import com.greenlend.greenlend.repository.AddressRepository;
import com.greenlend.greenlend.repository.GLUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GLUserServiceImpl implements GLUserService {

    private final GLUserRepository GLUserRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public GLUserServiceImpl(GLUserRepository GLUserRepository, AddressRepository addressRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.GLUserRepository = GLUserRepository;
        this.addressRepository = addressRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public GLUser findUserById(long userId) {
        Optional<GLUser> user = GLUserRepository.findById(userId);
        if (user.isPresent()) {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            GLUser authorizedUser = findByUsername(name);
            if (authorizedUser.getUserRole() == UserRole.ADMIN
                    || authorizedUser.getUserId() == user.get().getUserId()) {
                return user.get();
            } else {
                GLUser toRet = new GLUser();
                toRet.setUsername(user.get().getUsername());
                toRet.setFirstname(user.get().getFirstname());
                toRet.setLastname(user.get().getLastname());
                toRet.setLendOffers(user.get().getLendOffers());
                return toRet;
            }
        } else {
            throw new NotFoundException("User with id " + userId + " has not been found.");
        }
    }

    @Override
    public List<GLUser> findAllUsers() {
        return GLUserRepository.findAll();
    }

    @Override
    public GLUser createUser(GLUser newUser) {
        if (findByUsername(newUser.getUsername()) != null) {
            throw new UserAlreadyExistAuthenticationException("The username " + newUser.getUsername() + " is already taken.");
        }
        String password = newUser.getPassword();
        if (password == null || password.equals("")) {
            throw new WrongValueException("Password is required!");
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setUserRole(UserRole.USER);
        return GLUserRepository.save(newUser);
    }

    @Override
    public GLUser replaceUser(GLUser newUser, long userId) {
        return GLUserRepository.findById(userId).
                map(oldUser -> {
                    oldUser.setFirstname(newUser.getFirstname());
                    oldUser.setLastname(newUser.getLastname());
                    oldUser.setUsername(newUser.getUsername());
                    oldUser.setPassword(newUser.getPassword());
                    oldUser.setEmail(newUser.getEmail());
                    oldUser.setPhone(newUser.getPhone());
                    oldUser.setUserAddress(newUser.getUserAddress());
                    oldUser.setBookings(newUser.getBookings());
                    oldUser.setLendOffers(newUser.getLendOffers());
//                    oldUser.setSentMessages(newUser.getSentMessages());
//                    oldUser.setReceivedMessges(newUser.getReceivedMessages());
                    return GLUserRepository.save(oldUser);
        }).orElseGet(() -> {
            newUser.setUserId(userId);
            return GLUserRepository.save(newUser);
        });
    }

    @Override
    public void deleteUser(long userId) {
        GLUserRepository.deleteById(userId);
    }

    @Override
    public GLUser findByUsername(String username) {
        return GLUserRepository.findByUsername(username);
    }

    public GLUser addAddressToUser(Address address, long userId) {
        GLUser user = findUserById(userId);
        if(user == null) {
            throw new NotFoundException("User with this id does not exist");
        }
        if(user.getUserAddress() != null) {
            throw new ForbiddenException("User already has an address");
        }
        addressRepository.save(address);
        user.setUserAddress(address);
        return GLUserRepository.save(user);
    }

}
