package com.hallym.booker.api;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.dto.Login.LoginDto;
import com.hallym.booker.dto.Profile.ProfileDto;
import com.hallym.booker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileApiController {
    private final ProfileService profileService;

    /**
     * 프로필 등록
     */
//    @PostMapping("/profile/{uid}/new")
//    public String profileRegister(@RequestBody ProfileDto request){
//        Profile profile = new Profile(request.getUid(), request.getNickname(), request.g);
//        loginservice.join(login);
//        return "Register login Success";
//    }
}
