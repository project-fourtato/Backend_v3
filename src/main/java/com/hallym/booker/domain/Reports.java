package com.hallym.booker.domain;

import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Reports {
    @Id
    @Column(name = "report_uid")
    private String uid;

    @Enumerated(EnumType.STRING)
    private Rtype rtype;

    private String rimageUrl;
    private String rimageName;
    private String rcontents;
    private LocalDateTime rdatetime;
    private String rnickname;

    @ManyToMany(mappedBy = "reports")
    private List<Profile> profiles = new ArrayList<>();

    public void setProfiles(Profile profile) {
        this.profiles.add(profile);
        profile.setReports(this);
    }

    public Reports() {}
    public Reports(String uid, Rtype rtype, String rimageUrl, String rimageName, String rcontents, LocalDateTime rdatetime, String rnickname) {
        this.uid = uid;
        this.rtype = rtype;
        this.rimageUrl = rimageUrl;
        this.rimageName = rimageName;
        this.rcontents = rcontents;
        this.rdatetime = rdatetime;
        this.rnickname = rnickname;
    }

    //==생성 메서드==//
    public static Reports create(Profile profile, String uid, Rtype rtype, String rimageUrl, String rimageName, String rcontents, LocalDateTime rdatetime, String rnickname){
        Reports reports = new Reports(uid, rtype, rimageUrl, rimageName, rcontents, rdatetime, rnickname);
        reports.setProfiles(profile);
        return reports;
    }
}
