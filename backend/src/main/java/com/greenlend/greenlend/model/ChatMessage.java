package com.greenlend.greenlend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "chatMessage")
public class ChatMessage {
    @Id
    @GeneratedValue(generator = "message_generator")
    @SequenceGenerator(
            name = "message_generator",
            sequenceName = "message_sequence",
            initialValue = 1000
    )
    private long messageId;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentTime;

    @ManyToOne
    @JsonIgnore
    private Chat chat;

    @Column
    private boolean fromOwner;


    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public long getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public boolean isFromOwner() {
        return fromOwner;
    }

    public void setFromOwner(boolean fromOwner) {
        this.fromOwner = fromOwner;
    }
}
