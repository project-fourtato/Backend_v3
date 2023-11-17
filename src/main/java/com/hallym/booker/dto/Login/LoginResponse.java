package com.hallym.booker.dto.Login;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class LoginResponse {
    private String uid;

    public LoginResponse() {
    }
}
