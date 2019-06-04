package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.GLUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GLUserRepository extends JpaRepository<GLUser, Long> {
    GLUser findByUsername(String username);
}
