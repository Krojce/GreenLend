package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.model.LendOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat getChatByOfferAndInterested(LendOffer offer, GLUser interested);

    Chat getChatByOffer_LendOfferIdAndInterested(Long lendOfferId, GLUser interested);

    List<Chat> getChatsByOffer_OwnerOrInterested(GLUser owner, GLUser interested);
}
