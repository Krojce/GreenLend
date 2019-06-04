package com.greenlend.greenlend.model;

import javax.persistence.*;

@Entity(name = "lendOfferComment")
public class LendOfferComment {
    @Id
    @GeneratedValue(generator = "comment_generator")
    @SequenceGenerator(
            name = "comment_generator",
            sequenceName = "comment_sequence",
            initialValue = 1000
    )
    private long commentId;

    @Column
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCommentId() {
        return commentId;
    }

}
