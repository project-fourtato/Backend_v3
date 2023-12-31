package com.hallym.booker.api;

import com.hallym.booker.domain.Login;
import com.hallym.booker.dto.Login.*;
import com.hallym.booker.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginService loginservice;
    /**
     * 회원 등록
     */
    @PostMapping("/login/new")
    public SuccessResponse loginRegister(@RequestBody LoginDto request){
        Login login = new Login(request.getUid(), request.getPw(), request.getEmail(), request.getBirth());
        loginservice.join(login);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData("Register login Success");
        return successResponse;
    }

    /**
     * 아이디 중복검사
     */
    @GetMapping("/login/checkId/{uid}")
    public LoginCheckResponse idCheck(@PathVariable("uid") String uid){
        Boolean result = loginservice.checkId(uid);
        LoginCheckResponse loginCheckResponse = new LoginCheckResponse();
        loginCheckResponse.setData(result);
        return loginCheckResponse;
    }

    /**
     * 회원 수정 폼
     */
    @GetMapping("login/{uid}/edit")
    public LoginDto idFind(@PathVariable("uid") String uid){
        Login login = loginservice.findOne(uid);
        LoginDto loginDto = new LoginDto();
        loginDto.setUid(login.getUid());
        loginDto.setPw(login.getPw());
        loginDto.setEmail(login.getEmail());
        loginDto.setBirth(login.getBirth());
        return loginDto;
    }


    /**
     * 회원 로그인
     */
    @GetMapping("/login/uid={uid}&pw={pw}")
    public LoginResponse booksState(@PathVariable("uid") String uid, @PathVariable("pw") String pw) {
        Login l = loginservice.loginLogin(uid,pw);
        if(l == null){
            return null;
        }
        else{
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUid(uid);
            return loginResponse;
        }
    }

    /**
     * 회원 수정
     */
    @PutMapping("login/{uid}/edit")
    public SuccessResponse loginEdit(@PathVariable("uid") String uid, @RequestBody LoginRequest request){
        loginservice.updateLogin(uid, request.getPw(), request.getEmail(), request.getBirth());
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData("Edit Login Success");
        return successResponse;
    }
}
