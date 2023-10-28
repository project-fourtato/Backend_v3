package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
public class Login {
    @Id
    @Column(name = "login_uid")
    private String uid;
    private String nemail;
    private String nage;

    @OneToOne(mappedBy = "login")
    private Profile profile;

    public Login() {}

    public Login(String uid, String nemail, String nage) {
        this.uid = uid;
        this.nemail = nemail;
        this.nage = nage;
    }
}
