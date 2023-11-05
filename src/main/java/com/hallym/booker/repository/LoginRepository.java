package com.hallym.booker.repository;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//영속성 컨텍스트 주입
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    //회원 등록 및 수정
    public void save(Login login){
        em.persist(login);
    }

    //로그인 유효성 검사를 위해서만 쓰임
    public List<Login> findByUid(String uid){
        return em.createQuery("select m from Login m where m.uid = :uid", Login.class)
                .setParameter("uid", uid)
                .getResultList();
    }

    //회원 삭제
    public void deleteById(Login login){
        em.remove(login);
    }

    //회원 로그인
    public Login loginOne(String uid, String pw) {
        List<Login> logins = em.createQuery("select m from Login m where m.uid = :uid and m.pw = :pw", Login.class)
                .setParameter("uid", uid)
                .setParameter("pw", pw)
                .getResultList();
        if(logins.isEmpty()) return null;
        else return logins.get(0);
    }

    //회원 수정 폼
    public Login findOne(String uid){
        return em.find(Login.class, uid);
    }

}
