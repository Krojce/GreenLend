package com.greenlend.greenlend.service;

import com.google.common.annotations.VisibleForTesting;
import com.greenlend.greenlend.dto.ChatDTO;
import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.ChatMessage;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.repository.ChatRepository;
import com.greenlend.greenlend.repository.GLUserRepository;
import com.greenlend.greenlend.repository.MessageRepository;
import com.greenlend.greenlend.utils.DateConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatRepository dao;
    private GLUserRepository userDao;
    private MessageRepository messageDao;

    @Autowired
    public ChatServiceImpl(ChatRepository dao,
                           GLUserRepository userDao,
                           MessageRepository messageDao) {
        this.dao = dao;
        this.userDao = userDao;
        this.messageDao = messageDao;
    }

    @VisibleForTesting
    GLUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDao.findByUsername(username);
    }

    public List<ChatMessage> getMessages(Chat chat, Long offset) {
        List<ChatMessage> messages = messageDao.getChatMessageByChatOrderBySentTimeDesc(chat);
        List<ChatMessage> filtered = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            if (i >= offset * 16 && i < offset * 16 + 16) {
                filtered.add(messages.get(i));
            }
        }
        return filtered;
    }

    public List<ChatMessage> getMessagesAfter(Chat chat, Long timestamp) {
        Date d = DateConvertor.millisToDate(timestamp);
        List<ChatMessage> l = messageDao.getChatMessageByChatAndSentTimeGreaterThan(chat, d);
        return l;
    }

    public String sendMessage(String message, Long chatId) {
        Chat chat = dao.findById(chatId).get();
        GLUser sender = getCurrentUser();
        if (!chat.getInterested().equals(sender) &&
            !chat.getOffer().getOwner().equals(sender))
            return "403 – Forbidden";
        ChatMessage msg = new ChatMessage();
        msg.setFromOwner(sender.equals(chat.getOffer().getOwner()));
        msg.setSentTime(new Date());
        msg.setChat(chat);
        msg.setContent(message);

        messageDao.save(msg);

        chat.getMessages().add(msg);
        dao.save(chat);

        return "200 – OK";
    }


    @Override
    public List<Chat> getChats() {
        List<Chat> chats = dao.getChatsByOffer_OwnerOrInterested(getCurrentUser(), getCurrentUser());
//        chats.forEach((chat) -> {
//            if (chat.getMessages().size() > 0) {
//                List<ChatMessage> newMessages = new ArrayList<>();
//                newMessages.add(chat.getMessages().get(chat.getMessages().size() - 1));
//                chat.setMessages(newMessages);
//            }
//            chat.setOfferId(chat.getOffer().getLendOfferId());
//            if (chat.getInterested().getUsername().equals(getCurrentUser().getUsername())) {
//                chat.setChatWith(chat.getOffer().getOwner().getUsername());
//            } else {
//                chat.setChatWith(chat.getInterested().getUsername());
//            }
//            chat.setOffer(null);
//            chat.setInterested(null);
//        });
        return chats;
    }

    @Override
    public List<GLUser> getMembersInChat(Long chatId) {
        Chat chat = dao.findById(chatId).get();
        List<GLUser> members = new ArrayList<>();
        GLUser u1 = chat.getInterested();
        GLUser u2 = chat.getOffer().getOwner();
        GLUser wrap1 = new GLUser();
        GLUser wrap2 = new GLUser();
        wrap1.setUsername(u1.getUsername());
        wrap1.setFirstname(u1.getFirstname());
        wrap1.setLastname(u1.getLastname());
        wrap2.setUsername(u2.getUsername());
        wrap2.setFirstname(u2.getFirstname());
        wrap2.setLastname(u2.getLastname());
        members.add(wrap1);
        members.add(wrap2);
        return members;
    }

    @Override
    public ChatDTO convertToDTO(Chat chat) {
        ChatDTO dto = new ChatDTO(chat);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        GLUser user = userDao.findByUsername(username);
        dto.setLend(chat.getOffer().getOwner().equals(user));
        if(dto.isLend()) {
            dto.setOpponent(String.format("%s %s", chat.getInterested().getFirstname(), chat.getInterested().getLastname()));
        } else {
            dto.setOpponent(String.format("%s %s", chat.getOffer().getOwner().getFirstname(), chat.getOffer().getOwner().getLastname()));
        }
        return dto;
    }
}
