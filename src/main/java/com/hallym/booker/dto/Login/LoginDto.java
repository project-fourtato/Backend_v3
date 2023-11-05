package com.hallym.booker.dto.Login;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    private String uid;
    private String pw;
    private String email;
    private String birth;
}
