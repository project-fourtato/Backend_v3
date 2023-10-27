package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
public class Directmessage {
    private String messageid;
    private String senderuid;
    private String recipientuid;
    private LocalDateTime mdate;
    private int mcheck;
    private String mtitle;
    private String mcontents;

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
