package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.LendOfferComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendOfferCommentRepository extends JpaRepository<LendOfferComment, Long> {
}
