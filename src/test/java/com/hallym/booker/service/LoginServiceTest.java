package com.hallym.booker.service;

import com.hallym.booker.domain.Login;
import com.hallym.booker.repository.LoginRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) //@Autowird, @MockBean에 해당되는 것들에만 application context 로딩
@SpringBootTest //application context 전부 로딩
@Transactional
class LoginServiceTest {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    LoginService loginService;

    @Test
//    @Rollback(value = false) //DB에 저장시킴
    void join() throws Exception{
        //given
        Login login = new Login("130ekcn1r","hallym@gmail.com","30");
        //when
        String saveUid = loginService.join(login);
        //then
        assertEquals(login, loginRepository.findOne(saveUid));
    }

    @Test
    void findOne() throws Exception{
        //given
        Login login = new Login("130ekcn1r","hallym@gmail.com","30");
        //when
        //then
        assertEquals(login.getNemail(), loginRepository.findOne("130ekcn1r").getNemail());

    }

    @Test
//    @Rollback(value = false) //DB에 저장시킴
    void deleteOne() throws Exception{
        //given
        Login login = new Login("130ekr","hallym@gmail.com","30");
        //when
        String saveUid = loginService.join(login);
        String deleteLogin = loginService.deleteOne(saveUid);
        //then
        assertEquals(saveUid, deleteLogin);
    }
}