package com.hallym.booker;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class test {
    private final EntityManager em;

    public void save(Profile member) {
        em.persist(member); //영속성 컨텍스트에 member 객체를 넣음
    }

    public void loginsave(Login loginmember) {
        em.persist(loginmember); //영속성 컨텍스트에 member 객체를 넣음
    }

    public void Bookssave(Books booksmember) {
        em.persist(booksmember); //영속성 컨텍스트에 member 객체를 넣음
    }
    public Profile findOne(String id) {
        return em.find(Profile.class, id);
    }

    public List<Profile> findAll() {
        return em.createQuery("select m from profile m", Profile.class)
                .getResultList(); //member를 list로 만들어줌
    }

    public List<Profile> findByName(String nickname) {
        return em.createQuery("select m from profile m where m.nickname = :nickname", Profile.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
