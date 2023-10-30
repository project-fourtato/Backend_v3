package com.hallym.booker.repository;

import com.hallym.booker.domain.Followers;
import com.hallym.booker.domain.Journals;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JournalsRepository {
    private final EntityManager em;

    public void save(Journals journals) {
        if(journals.getJid() == null) { //처음 등록한 객체
            em.persist(journals);
        } else { //이미 db에 등록된 것을 가져온 것, update랑 비슷한 것
            em.merge(journals);
        }
    }

    public Journals findOne(String uid) {
        return em.find(Journals.class, uid);
    }

    public List<Journals> findAll() {
        return em.createQuery("select j from Journals j", Journals.class)
                .getResultList();
    }

    public void deleteById(Journals journals) {
        em.remove(journals);
    }
}
