package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Journals {
    @Id
    private String jid; //yyyy:mm:ff:hh:mm:ss:000
    private String journalsUserbid;
    private LocalDateTime pdatetime;
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userbid")
    private Books books;

    public Journals() {}
    public Journals(String jid, String journalsUserbid, LocalDateTime pdatetime, String ptitle, String pcontents, String pimageUrl, String pimagePath) {
        this.jid = jid;
        this.journalsUserbid = journalsUserbid;
        this.pdatetime = pdatetime;
        this.ptitle = ptitle;
        this.pcontents = pcontents;
        this.pimageUrl = pimageUrl;
        this.pimagePath = pimagePath;
    }
}
