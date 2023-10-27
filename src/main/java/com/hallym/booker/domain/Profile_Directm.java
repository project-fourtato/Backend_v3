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
public class Profile_Directm {
    private String uid;
    private String messageid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "uid")
    private Profile profile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "messageid")
    private List<Directmessage> directM = new ArrayList<>();

    public Profile_Directm() {}
    public Profile_Directm(String uid, String messageid) {
        this.uid = uid;
        this.messageid = messageid;
    }
}
