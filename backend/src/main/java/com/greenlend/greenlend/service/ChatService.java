package com.greenlend.greenlend.service;

import com.greenlend.greenlend.dto.ChatDTO;
import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.ChatMessage;
import com.greenlend.greenlend.model.GLUser;

import java.util.List;

public interface ChatService {

    List<ChatMessage> getMessages(Chat chatId, Long offset);

    List<ChatMessage> getMessagesAfter(Chat chat, Long timestamp);

    String sendMessage(String message, Long chat);

    List<Chat> getChats();

    List<GLUser> getMembersInChat(Long chat);

    ChatDTO convertToDTO(Chat chat);
}
