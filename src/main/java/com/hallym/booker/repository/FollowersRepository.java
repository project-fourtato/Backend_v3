package com.hallym.booker.repository;

import com.hallym.booker.domain.Followers;
import com.hallym.booker.domain.Followings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowersRepository {

    private final EntityManager em;

    public void save(Followers followers) {
        em.persist(followers);
    }

    public List<Followers> findAll() {
        return em.createQuery("select f from Followers f", Followers.class)
                .getResultList();
    }

    public void delete(Followers followers) {
        em.remove(followers);
    }
}
