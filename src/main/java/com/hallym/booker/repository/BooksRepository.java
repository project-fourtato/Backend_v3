package com.hallym.booker.repository;

import com.hallym.booker.domain.Books;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BooksRepository {
    private final EntityManager em;

    // 책 저장
    public void saveBooks(Books books) {
        em.persist(books);
    }

    // 책 삭제
    public void deleteBooks(Books books) {
        em.remove(books);
    }

    // 특정 프로필에 대한 모든 책 조회
    public List<Books> findAllBooksByProfile(String uid) {
        return em.createQuery("SELECT b FROM Books b WHERE b.profile.uid = :profileUid", Books.class)
                .setParameter("profileUid", uid)
                .getResultList();
    }

    // 특정 프로필에 대한 모든 책 상태 조회
    public List<Integer> findAllBookStateByProfile(String uid) {
        return em.createQuery("SELECT b.bookstate FROM Books b WHERE b.profile.uid = :profileUid", Integer.class)
                .setParameter("profileUid", uid)
                .getResultList();
    }

    // 특정 프로필에 대한 모든 책 판매 상태 조회
    public List<Integer> findAllSaleStateByProfile(String uid) {
        return em.createQuery("SELECT b.salestate FROM Books b WHERE b.profile.uid = :profileUid", Integer.class)
                .setParameter("profileUid", uid)
                .getResultList();
    }
}
