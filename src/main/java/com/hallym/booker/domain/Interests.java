package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Interests {
    private String uid;
    private String interest1;
    private String interest2;
    private String interest3;
    private String interest4;
    private String interest5;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "uid")
    private Profile profile;

    public Interests() {}

    public Interests(String uid, String interest1, String interest2, String interest3, String interest4, String interest5) {
        this.uid = uid;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.interest4 = interest4;
        this.interest5 = interest5;
    }
}
