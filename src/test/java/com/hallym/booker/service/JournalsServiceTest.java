package com.hallym.booker.service;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Journals;
import com.hallym.booker.repository.JournalsRepository;
import org.assertj.core.api.Assertions;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //junit 실행할 때, 스프링이랑 같이 엮어서 실행할래
@SpringBootTest //springboot 띄운 상태로 테스트
@Transactional //이게 test에 있음 기본적으로 rollback
public class JournalsServiceTest {

    @Autowired JournalsService journalsService;
    @Autowired JournalsRepository journalsRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(value = false)
    public void 독서록_추가() throws Exception {
        //given
        Books books = new Books("sdfegwttdh", "afwrgdwsed", 0, 0);
//        Journals journal = new Journals("esdflmwlef", LocalDateTime.now(),"해리포터 꿀잼", "해리포터 재밌었어용", "afiojwd.eufkjasw.wsafc", "egfqaef/eqgvff/wqsfd/");
//        journal.setBook(books);
        Journals journal = Journals.create(books,"esdflmwlef", LocalDateTime.now(),"해리포터 꿀잼", "해리포터 재밌었어용", "afiojwd.eufkjasw.wsafc", "egfqaef/eqgvff/wqsfd/");

        //when
        em.persist(books);
        journalsService.saveJournals(journal);
        List<Journals> items = journalsService.findAllJournalsByUserbid("sdfegwttdh");

        //then
        Assertions.assertThat(items.size()).isEqualTo(1);
    }

    @Test
    public void 독서록_삭제() throws Exception {
        //given
        Books books = new Books("kejafsdnhe", "ksdf-afc-aefa-afe", 0, 1);
        Journals journal = new Journals("qefkcnhdv", LocalDateTime.now(), "해리해리", "포터포터", "aefjoi.afj.afs", "edjsf/fad/ad/afced");
        journal.setBooks(books);

        //when
        em.persist(books);
        journalsService.deleteJournals(journal);
        Journals journalA = journalsService.findOne("qefkcnhdv");

        //then
        Assertions.assertThat(journalA).isNull();
    }

    @Test
    public void 독서록_수정() throws Exception {
        //given
        Books books = new Books("kejafsdnhe", "ksdf-afc-aefa-afe", 0, 1);
        Journals journalA = new Journals("qefkcnhdv", LocalDateTime.now(), "해리해리", "포터포터", "aefjoi.afj.afs", "edjsf/fad/ad/afced");
        journalA.setBooks(books);

        //when
        em.persist(books);
        journalsService.saveJournals(journalA); //첫 저장
        //Journals journalsA = journalsService.findOne(journalA.getJid());
        //String pcontentA = journalsA.getPcontents();

        journalsService.updateJournals("qefkcnhdv", null, "내용 수정 좀 했어용", "aefjoi.afj.afs", "edjsf/fad/ad/afced"); //수정 저장
        //Journals journalsAUpdate = journalsService.findOne(journalsA.getJid());
        //String pcontentAUpdate = journalsAUpdate.getPcontents();

        //then
        //em.flush();
        Assertions.assertThat("포터포터").isNotEqualTo(journalsService.findOne("qefkcnhdv").getPcontents());
    }
}