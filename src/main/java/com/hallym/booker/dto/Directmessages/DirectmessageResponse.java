package com.hallym.booker.dto.Directmessages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class DirectmessageResponse {
    private Long messageid;
    private String senderuid;
    private String recipientuid;
    private LocalDateTime mdate;
    private Integer mcheck;
    private String mtitle;
    private String mcontents;

    private String nickname;
    private String userimageUrl;
    private String userimageName;

    public DirectmessageResponse() {
    }
}
