package com.hallym.booker.repository;

import com.hallym.booker.domain.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
//영속성 컨텍스트 주입
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    //회원 등록
    public void save(Login login){
        em.persist(login);
    }

    //회원 찾기
    public Login findOne(String uid){
        return em.find(Login.class, uid);
    }

    //회원 삭제
    //주의 : 다른 DB들 다 삭제하고 마지막에 이 메서드 실행해야 함
    public void deleteById(Login login){
        em.remove(login);
    }
}
