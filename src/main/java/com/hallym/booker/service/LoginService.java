package com.hallym.booker.service;

import com.hallym.booker.domain.Login;
import com.hallym.booker.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    /**
     *  회원 등록
     */
    @Transactional //변경
    public String join(Login login){
        //중복 회원 검증 구현X
        loginRepository.save(login);
        return login.getUid();
    }

    /**
     * 회원 찾기
     */
    public Login findOne(String uid){
        return loginRepository.findOne(uid);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public String deleteOne(Login login){
        loginRepository.deleteById(login);
        return login.getUid();
    }
}
