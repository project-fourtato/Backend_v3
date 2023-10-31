package com.hallym.booker.repository;

import com.hallym.booker.domain.Directmessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DirectmessageRepository {
    private final EntityManager em;

    //쪽지 등록
    public void saveDirectmessage(Directmessage directmessage) {

        em.persist(directmessage);
    }

    //쪽지 찾기(조회)
    public Directmessage findDirectmessage(String messageid){
        return em.find(Directmessage.class, messageid);
    }

    //쪽지 삭제
    //주의 : 다른 DB들 다 삭제하고 마지막에 이 메서드 실행해야 함
    // Directmessage 삭제
    public void deleteDirectmessage(Directmessage directmessage) {
        em.remove(directmessage);
    }

    // 특정 발신자(senderUid)에 대한 모든 Directmessage 조회
    public List<Directmessage> findAllDirectmessagesBySender(String senderUid) {
        return em.createQuery("SELECT dm FROM Directmessage dm WHERE dm.senderuid = :senderUid", Directmessage.class)
                .setParameter("senderUid", senderUid)
                .getResultList();
    }

    // 특정 수신자(recipientUid)에 대한 모든 Directmessage 조회
    public List<Directmessage> findAllDirectMessagesByRecipient(String recipientUid) {
        return em.createQuery("SELECT dm FROM Directmessage dm WHERE dm.recipientuid = :recipientUid", Directmessage.class)
                .setParameter("recipientUid", recipientUid)
                .getResultList();
    }

}
