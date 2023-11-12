package com.hallym.booker.repository;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.awt.print.Book;
import java.util.LinkedList;
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

    //책 하나 조회
    public Books findOneBooks(String userbid) { return em.find(Books.class, userbid); }

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

    public List<Profile> findAllProfileByIsbn(String isbn) {
        return em.createQuery("SELECT b.profile FROM Books b WHERE b.isbn = :isbn", Profile.class)
                .setParameter("isbn", isbn)
                .setMaxResults(5)
                .getResultList();
    }

    // 책 검색에서 uid와 isbn을 통해 독서 상태 조회
    public Books SearchBooksByIsbn(String uid, String isbn) {
        Books books;
        try {
            books = em.createQuery("SELECT b FROM Books b WHERE b.isbn = :isbn and b.profile.uid = :profileUid", Books.class)
                    .setParameter("isbn", isbn)
                    .setParameter("profileUid", uid)
                    .getSingleResult();
        }catch(NoResultException e) {
            return null;
        }
        return books;

    }

    public List<Profile> findByIsbnAndSalesstate(String isbn){
        List<Books> books = em.createQuery("SELECT b FROM Books b WHERE b.isbn = :isbn and b.salestate = 1", Books.class)
                    .setParameter("isbn", isbn)
                    .getResultList();
        List<Profile> profiles = new LinkedList<>();
        for(int i=0;i<books.size();i++){
            profiles.add(books.get(i).getProfile());
        }
        return profiles;
    }
}
