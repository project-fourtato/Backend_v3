package com.hallym.booker.repository;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Reports;
import com.hallym.booker.domain.Rtype;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
//영속성 컨텍스트 주입
@RequiredArgsConstructor
public class ReportsRepository {
    private final EntityManager em;

    //리폿 추가
    public void save(Reports reports){
        em.persist(reports);
    }

    //리폿 uid를 통해 찾기
    public Reports findOne(String uid){
        return em.find(Reports.class, uid);
    }

    //리폿 타입별 리스트로 찾기
    public List<Reports> findByRtype(Rtype rtype){
        return em.createQuery("select m from Reports m where m.rtype = :rtype", Reports.class)
                .setParameter("rtype", rtype)
                .getResultList();
    }

    //리폿 삭제
    public void deleteById(String uid){
        Reports reports = findOne(uid);
        em.remove(reports);
    }
}
