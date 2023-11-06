package com.hallym.booker.dto.Profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ProfileRequest {
    private String uid;
    private String nickname;
    private String useriamgeUrl;
    private String userimageName;
    private String usermessage;

    private String uinterest1;
    private String uinterest2;
    private String uinterest3;
    private String uinterest4;
    private String uinterest5;
}
