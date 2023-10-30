package com.hallym.booker.repository;

import com.hallym.booker.domain.Followers;
import com.hallym.booker.domain.Followings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowingsRepository {

    private final EntityManager em;

    public void save(Followings followings) {
        em.persist(followings);
    }

    public List<Followings> findAll() {
        return em.createQuery("select f from Followings f", Followings.class)
                .getResultList();
    }

    public void deleteById(Followings followings) {
        em.remove(followings);
    }
}
