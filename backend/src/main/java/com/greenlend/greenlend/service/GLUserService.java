package com.greenlend.greenlend.service;

import com.greenlend.greenlend.model.Address;
import com.greenlend.greenlend.model.GLUser;

import java.util.List;

public interface GLUserService {
    GLUser findUserById(long userId);

    List<GLUser> findAllUsers();

    GLUser createUser(GLUser newUser);

    GLUser replaceUser(GLUser newUser, long userId);

    void deleteUser(long userId);

    GLUser findByUsername(String username);

    GLUser addAddressToUser(Address address, long userId);
}
