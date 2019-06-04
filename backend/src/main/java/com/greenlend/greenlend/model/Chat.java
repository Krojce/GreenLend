package com.greenlend.greenlend.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(generator = "chat_generator")
    @SequenceGenerator(
            name = "chat_generator",
            sequenceName = "chat_sequence",
            initialValue = 1000
    )
    private long chatId;
    @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChatMessage> messages;

    @ManyToOne
    private LendOffer offer;

    @ManyToOne
    private GLUser interested;

//    @Transient
//    private Long offerId;
//    @Transient
//    private String chatWith;

    public long getChatId() {
        return chatId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public LendOffer getOffer() {
        return offer;
    }

    public void setOffer(LendOffer offer) {
        this.offer = offer;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

//    public Long getOfferId() {
//        return offerId;
//    }
//
//    public void setOfferId(Long offerId) {
//        this.offerId = offerId;
//    }
//
//    public String getChatWith() {
//        return chatWith;
//    }
//
//    public void setChatWith(String chatWith) {
//        this.chatWith = chatWith;
//    }

    public GLUser getInterested() {
        return interested;
    }

    public void setInterested(GLUser interested) {
        this.interested = interested;
    }
}
