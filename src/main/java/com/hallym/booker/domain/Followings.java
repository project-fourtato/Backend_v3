package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Followings {
    @Id
    @Column(name = "followings_uid")
    private String uid;
    private String following_profile_uid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_uid")
    private Profile profile;

    public Followings() {}

    public Followings(String uid, String following_profile_uid) {
        this.uid = uid;
        this.following_profile_uid = following_profile_uid;
    }
}
