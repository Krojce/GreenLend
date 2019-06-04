package com.greenlend.greenlend.repository;

import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> getChatMessageByChatOrderBySentTimeDesc(Chat chat);
    List<ChatMessage> getChatMessageByChatAndSentTimeGreaterThan(Chat chat, Date sentTime);
}
