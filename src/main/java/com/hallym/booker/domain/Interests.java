package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Interests {
    @Id
    private String interestsUid;
    private String uinterest1;
    private String uinterest2;
    private String uinterest3;
    private String uinterest4;
    private String uinterest5;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profileUid")
    private Profile profile;

    public Interests() {}

    public Interests(String interestsUid, String uinterest1, String uinterest2, String uinterest3, String uinterest4, String uinterest5) {
        this.interestsUid = interestsUid;
        this.uinterest1 = uinterest1;
        this.uinterest2 = uinterest2;
        this.uinterest3 = uinterest3;
        this.uinterest4 = uinterest4;
        this.uinterest5 = uinterest5;
    }
}
