package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Followings {
    private String uid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "uid")
    private List<Profile> followinguid = new ArrayList<>();

    public Followings() {}
    public Followings(String uid) {
        this.uid = uid;
    }
}
