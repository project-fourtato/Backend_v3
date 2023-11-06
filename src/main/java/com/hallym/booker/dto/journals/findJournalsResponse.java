package com.hallym.booker.dto.journals;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class findJournalsResponse {
    private String jid;
    private LocalDateTime pdatetime;
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimageName;
}
