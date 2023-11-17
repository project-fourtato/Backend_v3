package com.hallym.booker.dto.Profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ProfileDto {
    private String uid;
    private String nickname;
    private String useriamgeUrl;
    private String userimageName;
    private String usermessage;

    public ProfileDto() {
    }
}
