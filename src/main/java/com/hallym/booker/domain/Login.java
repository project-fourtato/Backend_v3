package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Login {
    @Id
    @Column(name = "login_uid")
    private String uid;
    private String pw;
    private String email;
    private String birth;

    // // foreign key constraint fails 에러를 위한 생쿼리문
    @OneToOne(mappedBy = "login", cascade = CascadeType.REMOVE)
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.setLogin(this);
    }

    public Login() {}

    public Login(String uid, String pw, String email, String birth) {
        this.uid = uid;
        this.pw = pw;
        this.email = email;
        this.birth = birth;
    }

    //==수정 메서드==//
    public void change(String pw, String email, String birth) {
        this.pw = pw;
        this.email = email;
        this.birth = birth;
    }

    //==생성 메서드==//
    public static Login create(Profile profile, String uid, String pw, String email, String birth){
        Login login = new Login(uid, pw, email, birth);
        login.setProfile(profile);
        return login;
    }
}
