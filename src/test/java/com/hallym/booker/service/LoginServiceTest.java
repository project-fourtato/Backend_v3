//package com.hallym.booker.service;
//
//import com.hallym.booker.domain.Login;
//import com.hallym.booker.repository.LoginRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringRunner.class) //@Autowird, @MockBean에 해당되는 것들에만 application context 로딩
//@SpringBootTest //application context 전부 로딩
//@Transactional
//class LoginServiceTest {
//
//    @Autowired
//    LoginRepository loginRepository;
//
//    @Autowired
//    LoginService loginService;
//
//    @Test
////    @Rollback(value = false) //DB에 저장시킴
//    void join() throws Exception{
//        //given
//        Login login = new Login("hallym","hallym123","hallym@gmail.com","2023-10-10");
//        //when
//        String saveUid = loginService.join(login);
//        //then
//        assertEquals(login, loginRepository.findByUid(saveUid));
//    }
//
//    @Test
//    void findOne() throws Exception{
//        //given
//        Login login = new Login("hallym","hallym123","hallym@gmail.com","2023-10-10");
//        //when
//        String saveUid = loginService.join(login);
//        //then
//     //   assertEquals(login.getEmail(), loginRepository.findByUid("hallym").getEmail());
//
//    }
//
//    @Test
////    @Rollback(value = false) //DB에 저장시킴
//    void deleteOne() throws Exception{
//        //given
//        Login login = new Login("hallym","hallym123","hallym@gmail.com","2023-10-10");
//        //when
//        loginService.join(login);
//    //    String deleteLogin = loginService.deleteOne(login);
//        Login findLogin = loginService.findOne(login.getUid());
//        //then
//        Assertions.assertThat(findLogin).isNull();
//    }
//
//    @Test
//    void 아이디중복예외() throws Exception{
//        //given
//        Login login1 = new Login("hallym","hallym123","hallym@gmail.com","2023-10-10");
//        Login login2 = new Login("hallym","hallym456","hallym@gmail.com","2023-10-7");
//
//        //when
//        loginService.join(login1);
//
//        //then
//        assertThrows(IllegalStateException.class, () -> {
//            loginService.join(login2);
//        });
//    }
//
//    @Test
//    void 아이디중복검사() throws Exception{
//        //given
//        Login login1 = new Login("hallym","hallym123","hallym@gmail.com","2023-10-10");
//
//        //when
//        loginService.join(login1);
//
//        //then
//        Boolean a = loginService.checkId("hallym");
//        Assertions.assertThat(a).isFalse(); //중복
//    }
//
//    @Test
//    void 로그인실패() throws Exception{
//        //given
//        Login login1 = new Login("hallym","hallym123","hallym@gmail.com","2023-10-10");
//        loginService.join(login1);
//        //when
//        Login login = loginService.loginLogin("hallym","hallym124");
//        Assertions.assertThat(login).isNull(); //회원 정보가
//
//    }
//}