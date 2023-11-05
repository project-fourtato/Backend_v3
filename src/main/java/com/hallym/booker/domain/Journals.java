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
    private LocalDateTime pdatetime;
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimageName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userbid")
    private Books books;

    public void setBooks(Books books) {
        this.books = books;
    }

    public Journals() {}
    public Journals(String jid, LocalDateTime pdatetime, String ptitle, String pcontents, String pimageUrl, String pimageName) {
        this.jid = jid;
        this.pdatetime = pdatetime;
        this.ptitle = ptitle;
        this.pcontents = pcontents;
        this.pimageUrl = pimageUrl;
        this.pimageName = pimageName;
    }

    //==수정 메서드==//
    public Journals change(String ptitle, String pcontents, String pimageUrl, String pimageName) {
        this.ptitle = ptitle;
        this.pcontents = pcontents;
        this.pimageUrl = pimageUrl;
        this.pimageName = pimageName;

        return this;
    }

    //==생성 메서드==//
    public static Journals create(Books books, String jid, LocalDateTime pdatetime, String ptitle, String pcontents, String pimageUrl, String pimageName){
        Journals journals = new Journals(jid,pdatetime, ptitle, pcontents, pimageUrl, pimageName);
        journals.setBooks(books);
        return journals;
    }
}
