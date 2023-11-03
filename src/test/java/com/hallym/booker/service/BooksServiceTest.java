package com.hallym.booker.service;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Journals;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.BooksRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.property.access.internal.PropertyAccessFieldImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BooksServiceTest {

    @Autowired
    BooksService booksService;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    EntityManager em;

    @Test // 책 저장
//    @Rollback(value = false) //DB에 저장시킴

    public void saveBooks() throws Exception {

        // Given
        Profile profile = new Profile("1234", "yarong", "userimageUrl", "userimagePath", "나는 돌");
        Journals journal = new Journals("qefkcnhdv", LocalDateTime.now(), "해리해리", "포터포터", "aefjoi.afj.afs", "edjsf/fad/ad/afced");
        Books books = new Books("yarong1234", "isbn", 0, 0);


        // When
        em.persist(profile);
        em.persist(journal);
        booksService.saveBooks(books);
        List<Books> findAllBooksByProfile = booksService.findAllBooksByProfile("1234"); // 해당 Profile의 책 목록을 가져옴

        // Then
        Assertions.assertThat(findAllBooksByProfile.size()).isEqualTo(1); // 책이 1개여야 함
    }

    @Test // 해당 유저가 가지고 있는 책 조회
    public void findAllBooksByProfile() throws Exception {
        // Given
        Profile profile = new Profile("12345", "yarong5", "userimageUrl5", "userimagePath5", "나는 돌5");

        Books books1 = new Books("yarong1234567", "isbn1", 0, 0);
        books1.setProfile(profile); // Books와 Profile을 연결

        Books books2 = new Books("yarong1234567676", "isbn2", 1, 1);
        books2.setProfile(profile);

        // When
        em.persist(profile);
        booksService.saveBooks(books1);
        booksService.saveBooks(books2);
        List<Books> findAllBooksByProfile = booksService.findAllBooksByProfile("12345"); // 해당 Profile의 책 목록을 가져옴

        // Then
//        Assertions.assertThat(findAllBooksByProfile).isNull();
        Assertions.assertThat(findAllBooksByProfile.size()).isEqualTo(2); // 책이 2개여야 함
    }

    @Test // 책 삭제
    public void deleteBooks() throws Exception {
        // Given
        Profile profile = new Profile("12346", "yarong6", "userimageUrl6", "userimagePath6", "나는 돌6");

        Books books = new Books("yarong12346", "isbn", 0, 0);
        books.setProfile(profile); // Books와 Profile을 연결

        // When
        em.persist(profile); // Profile 정보를 영구 저장소에 저장
        booksService.deleteBooks(books); // 책 정보를 삭제
        List<Books> findAllBooksByProfile = booksService.findAllBooksByProfile("12346"); // 해당 Profile의 책 목록을 가져옴

        // Then
        //Assertions.assertThat(findAllBooksByProfile).isNull(); // 삭제된 책 정보는 null이어야 함
        Assertions.assertThat(findAllBooksByProfile.size()).isEqualTo(0); // 책이 0개여야 함
    }



    @Test // 특정 프로필에 속하는 모든 책 상태 검색
    public void findAllBookStateByProfile() {
        // Given
        Profile profile = new Profile("12347", "yarong7", "userimageUrl7", "userimagePath7", "나는 돌7");

        Books books1 = new Books("yarong12347", "isbn1", 0, 0);
        books1.setProfile(profile); // Books와 Profile을 연결

        Books books2 = new Books("yarong123456567", "isbn2", 1, 1);
        books2.setProfile(profile);

        // When
        em.persist(profile);
        booksService.saveBooks(books1);
        booksService.saveBooks(books2);
        List<Integer> bookStates = booksService.findAllBookStateByProfile("12347");

        // Then
        Assertions.assertThat(bookStates.size()).isEqualTo(2);
        Assertions.assertThat(bookStates).contains(0, 1);
    }

    @Test // 특정 프로필에 속하는 모든 판매 상태 검색
    public void findAllSaleStateByProfile() {
        // Given
        Profile profile = new Profile("12348", "yarong8", "userimageUrl8", "userimagePath8", "나는 돌8");

        Books books1 = new Books("yarong12348", "isbn1", 0, 0);
        books1.setProfile(profile); // Books와 Profile을 연결

        Books books2 = new Books("yarong12387654548", "isbn2", 1, 1);
        books2.setProfile(profile);

        // When
        em.persist(profile);
        booksService.saveBooks(books1);
        booksService.saveBooks(books2);
        List<Integer> saleStates = booksService.findAllSaleStateByProfile("12348");

        // Then
        Assertions.assertThat(saleStates.size()).isEqualTo(2);
        Assertions.assertThat(saleStates).contains(0, 1);
    }

    @Test
    public void UpdateBooks() {
        //given
        Profile profile = new Profile("12348", "yarong", "userimageUrl", "userimagePath", "판타지 좋아해요");

        Books books = new Books("yarong12348", "isbn1", 0, 0);
        books.setProfile(profile); // Books와 Profile을 연결
        em.persist(profile);
        booksService.saveBooks(books);

        //when
        booksService.updateBooks("yarong12348", 0, 1);

        //then
        em.flush();
        Assertions.assertThat(1).isEqualTo(booksRepository.findOneBooks("yarong12348").getSalestate());
    }
}
