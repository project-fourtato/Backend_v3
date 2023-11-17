package com.hallym.booker.dto.Login;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class LoginDto {
    private String uid;
    private String pw;
    private String email;
    private String birth;

    public LoginDto() {
    }
}
