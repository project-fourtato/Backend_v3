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
    private String followersUid;
    private String follower_profile_uid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profileUid")
    private Profile profile;

    public Followers() {}

    public Followers(String followersUid, String follower_profile_uid) {
        this.followersUid = followersUid;
        this.follower_profile_uid = follower_profile_uid;
    }
}
