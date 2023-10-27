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
public class Profile_Reports {
    private String uid;
    private String reportuid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "uid")
    private Profile profile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reportuid")
    private List<Reports> reports = new ArrayList<>();

    public Profile_Reports() {}
    public Profile_Reports(String uid, String reportuid) {
        this.uid = uid;
        this.reportuid = reportuid;
    }
}
