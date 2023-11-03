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
        em.persist(journals);
    }

    /*public void update(Journals journals) {
        em.merge(journals);
    }*/

    public Journals findOne(String uid) {
        return em.find(Journals.class, uid);
    }

    public List<Journals> findAll() {
        return em.createQuery("select j from Journals j", Journals.class)
                .getResultList();
    }

    public void delete(Journals journals) {
        em.remove(journals);
    }
}
