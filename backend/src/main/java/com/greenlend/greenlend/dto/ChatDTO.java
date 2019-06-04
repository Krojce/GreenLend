package com.greenlend.greenlend.dto;

import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.ChatMessage;

import java.util.List;

public class ChatDTO {

    private long id;
    private String offerName;
    private List<ChatMessage> messages;

    private String opponent;
    private boolean isLend;

    public ChatDTO() {}

    public ChatDTO(Chat chat) {
        this.id = chat.getChatId();
        this.offerName = chat.getOffer().getName();
        this.messages = chat.getMessages();
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLend() {
        return isLend;
    }

    public void setLend(boolean lend) {
        isLend = lend;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
