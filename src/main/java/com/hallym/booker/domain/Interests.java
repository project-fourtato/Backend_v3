package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Interests {
    @Id
    @Column(name = "interests_uid")
    private String uid;
    private String uinterest1;
    private String uinterest2;
    private String uinterest3;
    private String uinterest4;
    private String uinterest5;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_uid")
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Interests() {}

    public Interests(String uid, String uinterest1, String uinterest2, String uinterest3, String uinterest4, String uinterest5) {
        this.uid = uid;
        this.uinterest1 = uinterest1;
        this.uinterest2 = uinterest2;
        this.uinterest3 = uinterest3;
        this.uinterest4 = uinterest4;
        this.uinterest5 = uinterest5;
    }

    //==수정 메서드==//
    public void change(String uinterest1, String uinterest2, String uinterest3, String uinterest4,  String uinterest5) {
//        if(uinterest2 == null) {
//            this.uinterest1 = uinterest1;
//        }
//        if(uinterest3 == null) {
//            this.uinterest1 = uinterest1;
//            this.uinterest2 = uinterest2;
//        }
//        if(uinterest4 == null) {
//            this.uinterest1 = uinterest1;
//            this.uinterest2 = uinterest2;
//            this.uinterest3 = uinterest3;
//        }
//        if(uinterest5 == null) {
//            this.uinterest1 = uinterest1;
//            this.uinterest2 = uinterest2;
//            this.uinterest3 = uinterest3;
//            this.uinterest4 = uinterest4;
//        }
//        if(uinterest5 != null) {
            this.uinterest1 = uinterest1;
            this.uinterest2 = uinterest2;
            this.uinterest3 = uinterest3;
            this.uinterest4 = uinterest4;
            this.uinterest5 = uinterest5;
    }

    //==생성 메서드==//
    public static Interests create(Profile profile, String uid, String uinterest1, String uinterest2, String uinterest3, String uinterest4,  String uinterest5){
        Interests interests = new Interests(uid,uinterest1,uinterest2, uinterest3, uinterest4, uinterest5);
        interests.setProfile(profile);
        return interests;
    }

}
