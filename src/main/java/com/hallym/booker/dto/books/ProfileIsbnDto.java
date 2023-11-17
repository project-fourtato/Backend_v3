package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileIsbnDto {
    private String uid;
    private String nickname;
    private String useriamgeUrl;
    private String userimageName;
    private String usermessage;

    public ProfileIsbnDto() {
    }
}
