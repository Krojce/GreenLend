package com.greenlend.greenlend.dto;

import com.greenlend.greenlend.model.GLUser;

public class OwnerDTO {
    private String firstname;
    private String lastname;
    private long id;
    // private String email;
    // private String username;

    public OwnerDTO() {

    }

    public OwnerDTO(GLUser user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.id = user.getUserId();
        // this.email = user.getEmail();
        // this.username = user.getUsername();
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    // public String getEmail() {
    //     return email;
    // }
    // public void setEmail(String email) {
    //     this.email = email;
    // }
    // public String getUsername() {
    //     return username;
    // }
    // public void setUsername(String username) {
    //     this.username = username;
    // }
}
