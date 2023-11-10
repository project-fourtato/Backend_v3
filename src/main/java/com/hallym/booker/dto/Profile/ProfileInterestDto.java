package com.hallym.booker.dto.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ProfileInterestDto {
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

    public ProfileInterestDto(){

    }
    public ProfileInterestDto(String uid, String nickname, String useriamgeUrl, String userimageName, String usermessage, String uinterest1, String uinterest2, String uinterest3, String uinterest4, String uinterest5) {
        this.uid = uid;
        this.nickname = nickname;
        this.useriamgeUrl = useriamgeUrl;
        this.userimageName = userimageName;
        this.usermessage = usermessage;
        this.uinterest1 = uinterest1;
        this.uinterest2 = uinterest2;
        this.uinterest3 = uinterest3;
        this.uinterest4 = uinterest4;
        this.uinterest5 = uinterest5;
    }
}
