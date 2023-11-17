package com.hallym.booker.dto.Profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ProfileCheckResponse {
    private Boolean data;

    public ProfileCheckResponse() {
    }
}