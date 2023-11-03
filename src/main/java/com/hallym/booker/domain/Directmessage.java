package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Directmessage {
    @Id
    private String messageid;
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

    public Directmessage(String messageid, String senderuid, String recipientuid, LocalDateTime mdate, int mcheck, String mtitle, String mcontents) {
        this.messageid = messageid;
        this.senderuid = senderuid;
        this.recipientuid = recipientuid;
        this.mdate = mdate;
        this.mcheck = mcheck;
        this.mtitle = mtitle;
        this.mcontents = mcontents;
    }
}
