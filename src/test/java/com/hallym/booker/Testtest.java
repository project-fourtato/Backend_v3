package com.hallym.booker;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.awt.print.Book;

@RunWith(SpringRunner.class) //junit 실행할 때, 스프링이랑 같이 엮어서 실행할래
@SpringBootTest //springboot 띄운 상태로 테스트
@Transactional //이게 test에 있음 기본적으로 rollback
public class Testtest {
    @Autowired
    servicetest memberService;
    @Autowired test memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given - 이런게 주어졌을 때
        Profile member = new Profile("SUJDBVFKEFC", "SDVNFLSDJKV", "DASKCNDSC", "DKJVNC", "ASDVSDV");

        Login login = new Login("SUJDBVFKEFC", "wubfdksdc", "sujkdfh");
        //when - 이렇게 하면
        memberService.loginjoin(login);
        memberService.join(member);

        /*Books book = new Books("egfsd", "ergfd", "Ergfdvc", 0, 0);
        memberService.booksjoin(book);*/


        //then - 이렇게 된다
        em.flush(); //insert 쿼리 보고싶으면
    }
}
