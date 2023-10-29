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
    private String reportUid;

    @Enumerated(EnumType.STRING)
    private Rtype rtype;

    private String rimageUrl;
    private String rimagePath;
    private String rcontents;
    private LocalDateTime rdatetime;
    private String rnickname;

    @ManyToMany(mappedBy = "reports")
    private List<Profile> profiles = new ArrayList<>();

    public Reports() {}
    public Reports(String reportUid, Rtype rtype, String rimageUrl, String rimagePath, String rcontents, LocalDateTime rdatetime, String rnickname) {
        this.reportUid = reportUid;
        this.rtype = rtype;
        this.rimageUrl = rimageUrl;
        this.rimagePath = rimagePath;
        this.rcontents = rcontents;
        this.rdatetime = rdatetime;
        this.rnickname = rnickname;
    }
}
