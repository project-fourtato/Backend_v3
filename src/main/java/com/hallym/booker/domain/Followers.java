package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Followers {
    @Id
    @Column(name = "followers_uid")
    private String uid;
    private String follower_profile_uid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profileUid")
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Followers() {}

    public Followers(String uid, String follower_profile_uid) {
        this.uid = uid;
        this.follower_profile_uid = follower_profile_uid;
    }
}
