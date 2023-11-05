package com.hallym.booker.service;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        validateDuplicateMember(login); //중복 회원 검증
        loginRepository.save(login);
        return login.getUid();
    }

    private void validateDuplicateMember(Login login) {
        //중복 예외 처리
        List<Login> findMembers = loginRepository.findByUid(login.getUid());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 아이디 중복 검사
     */
    public Boolean checkId(String uid){
        List<Login> findMembers = loginRepository.findByUid(uid);
        if(!findMembers.isEmpty()){
            return false; //회원 존재
        }
        else {
            return true; //회원 없음
        }
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

    /**
     * 회원 수정
     */
    @Transactional
    public void updateLogin(String uid, String pw, String email, String birth) {
        Login findLogin = loginRepository.findOne(uid);
        findLogin.change(pw, email, birth);
    }

    /**
     * 로그인
     */
    @Transactional
    public Login loginLogin(String uid, String pw){
        Login findLogin = loginRepository.loginOne(uid,pw);
        return findLogin;
    }
}
