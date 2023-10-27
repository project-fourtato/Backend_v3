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
    private String userbid;
    private String uid;
    private String isbn;
    private int bookstate;
    private int salestate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "uid")
    private Profile profile;

    @OneToMany(mappedBy = "books")
    private List<Journals> journals = new ArrayList<>();

    public Books() {}

    public Books(String userbid, String uid, String isbn, int bookstate, int salestate, Profile profile) {
        this.userbid = userbid;
        this.uid = uid;
        this.isbn = isbn;
        this.bookstate = bookstate;
        this.salestate = salestate;
        this.profile = profile;
    }
}
