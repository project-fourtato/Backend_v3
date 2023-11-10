package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter

public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    private String toUserId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name= "fromUserId", referencedColumnName = "profile_uid")
    private Profile fromUserId;

    public void setProfile(Profile profile) {
        this.fromUserId = profile;
    }

    public Follow() {

    }
    public Follow(String toUserId) {
        this.toUserId = toUserId;
    }

    //==생성 메서드==//
    public static Follow create(Profile profile, String toUserId){
        Follow follows = new Follow(toUserId);
        follows.setProfile(profile);
        return follows;
    }
}