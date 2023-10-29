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
    private String followingsUid;
    private String following_profile_uid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profileUid")
    private Profile profile;

    public Followings() {}

    public Followings(String followingsUid, String following_profile_uid) {
        this.followingsUid = followingsUid;
        this.following_profile_uid = following_profile_uid;
    }
}
