package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Directmessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageid;
    private String senderuid;
    private String recipientuid;
    private LocalDateTime mdate;
    private Integer mcheck;
    private String mtitle;
    private String mcontents;

    @ManyToMany(mappedBy = "directmessages")
    private List<Profile> profiles = new ArrayList<>();

    public void setProfiles(Profile profile) {
        this.profiles.add(profile);
        profile.setDirectmessages(this);
    }

    public Directmessage() {}

    public Directmessage(String senderuid, String recipientuid, LocalDateTime mdate, int mcheck, String mtitle, String mcontents) {
        this.senderuid = senderuid;
        this.recipientuid = recipientuid;
        this.mdate = mdate;
        this.mcheck = mcheck;
        this.mtitle = mtitle;
        this.mcontents = mcontents;
    }

    //==수정 메서드==// (11/15 추가)
    public void change(int mcheck) {
        this.mcheck = mcheck;
    }

    //==생성 메서드==//
    public static Directmessage create(Profile profile, String senderuid, String recipientuid, LocalDateTime mdate, int mcheck, String mtitle, String mcontents){
        Directmessage directmessage = new Directmessage(senderuid, recipientuid, mdate, mcheck,mtitle, mcontents);
        directmessage.setProfiles(profile);
        return directmessage;
    }
}
