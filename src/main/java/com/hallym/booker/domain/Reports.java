package com.hallym.booker.domain;

import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reports {
    private String reportuid;

    @Enumerated(EnumType.STRING)
    private Rtype rtype;

    private String rimageUrl;
    private String rimagePath;
    private String rcontents;
    private LocalDateTime rdatetime;
    private String rnickname;

    public Reports() {}
    public Reports(String reportuid, Rtype rtype, String rimageUrl, String rimagePath, String rcontents, LocalDateTime rdatetime, String rnickname) {
        this.reportuid = reportuid;
        this.rtype = rtype;
        this.rimageUrl = rimageUrl;
        this.rimagePath = rimagePath;
        this.rcontents = rcontents;
        this.rdatetime = rdatetime;
        this.rnickname = rnickname;
    }
}
