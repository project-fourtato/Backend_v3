package com.hallym.booker.service;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;

    // 책 정보 저장
    @Transactional
    public String saveBooks(Books books) {
        booksRepository.saveBooks(books);
        return books.getUserbid();
    }

    // 책 정보 삭제
    @Transactional
    public void deleteBooks(Books books) {
        booksRepository.deleteBooks(books);
    }
    
    @Transactional
    public void updateBooks(String userbid, int bookstate, int salestate) {
        Books books = booksRepository.findOneBooks(userbid);
        books.change(bookstate, salestate);
    }

    public Books findOneBooks(String userbid) { return booksRepository.findOneBooks(userbid); }

    // 특정 프로필에 속하는 모든 책 정보 검색
    public List<Books> findAllBooksByProfile(String uid) {
        return booksRepository.findAllBooksByProfile(uid);
    }

    // 특정 프로필에 속하는 모든 책 상태 검색
    public List<Integer> findAllBookStateByProfile(String uid) {
        return booksRepository.findAllBookStateByProfile(uid);
    }

    // 특정 프로필에 속하는 모든 판매 상태 검색
    public List<Integer> findAllSaleStateByProfile(String uid) {
        return booksRepository.findAllSaleStateByProfile(uid);
    }

    public List<Profile> findAllProfileByIsbn(String isbn) {
        return booksRepository.findAllProfileByIsbn(isbn);
    }
}
