package com.hallym.booker.dto.journals;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JournalsDto {
    private String jid; //yyyy:mm:ff:hh:mm:ss:000
    private LocalDateTime pdatetime;
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimageName;

    public JournalsDto() {
    }
}
