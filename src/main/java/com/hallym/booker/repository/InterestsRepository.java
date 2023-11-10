package com.hallym.booker.repository;

import com.hallym.booker.domain.Interests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterestsRepository {
    private final EntityManager em;

    // 관심사 저장
    public void saveInterests(Interests interests) {
        em.persist(interests);
    }

    // 관십사 조회
    public Interests findInterests(String uid) {
        return em.find(Interests.class, uid);
    }

    // 관십사 수정
    /*public Interests updateInterests(Interests interests) {
        return em.merge(interests);
    }*/

    // 관십사 삭제
    public void deleteInterests(Interests interests) {
        em.remove(interests);
    }

    // 특정 프로필에 대한 모든 관심사 조회
    public List<Interests> findAllInterestsByProfile(String uid) {
        return em.createQuery("SELECT i FROM Interests i WHERE i.profile.uid = :profileUid", Interests.class)
                .setParameter("profileUid", uid)
                .getResultList();
    }
    //모든 관심사들 조회(본인 제외)
    public List<Interests> findAllInterests(String uid) {
        return em.createQuery("SELECT i FROM Interests i where i.profile.uid not in :profileUid", Interests.class)
                .setParameter("profileUid", uid)
                .getResultList();
    }
}