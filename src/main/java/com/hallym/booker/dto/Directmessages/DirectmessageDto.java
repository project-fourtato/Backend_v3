package com.hallym.booker.dto.Directmessages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DirectmessageDto {
    private String messageid;
    private String senderuid;
    private String recipientuid;
    private LocalDateTime mdate;
    private Integer mcheck;
    private String mtitle;
    private String mcontents;

}