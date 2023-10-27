package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class Login {
    private String uid;
    private String nemail;
    private String nage;

    public Login() {}

    public Login(String uid, String nemail, String nage) {
        this.uid = uid;
        this.nemail = nemail;
        this.nage = nage;
    }
}
