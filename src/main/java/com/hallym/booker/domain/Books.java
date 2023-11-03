package com.hallym.booker.domain;

import lombok.Getter;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Books {
    @Id
    private String userbid;

    private String isbn;
    private Integer bookstate;
    private Integer salestate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profileUid")
    private Profile profile;

    @OneToMany(mappedBy = "books")
    private List<Journals> journals = new ArrayList<>();

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setJournals(Journals journals) {
        this.journals.add(journals);
        journals.setBooks(this);
    }

    public Books() {}

    public Books(String userbid, String isbn, int bookstate, int salestate) {
        this.userbid = userbid;
        this.isbn = isbn;
        this.bookstate = bookstate;
        this.salestate = salestate;
    }

    //==수정 메서드==//
    public void change(int bookstate, int salestate) {
        this.bookstate = bookstate;
        this.salestate = salestate;
    }
}
