package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Journals {
    @Id
    private String jid; //yyyy:mm:ff:hh:mm:ss:000

    @Column(name = "journals_userbid")
    private String userbid;
    private LocalDateTime pdatetime;
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userbid")
    private Books books;

    public Journals() {}
    public Journals(String jid, String userbid, LocalDateTime pdatetime, String ptitle, String pcontents, String pimageUrl, String pimagePath) {
        this.jid = jid;
        this.userbid = userbid;
        this.pdatetime = pdatetime;
        this.ptitle = ptitle;
        this.pcontents = pcontents;
        this.pimageUrl = pimageUrl;
        this.pimagePath = pimagePath;
    }
}
