package com.hallym.booker.service;

import com.hallym.booker.domain.Directmessage;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.DirectmessageRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DirectmessageServiceTest {

    @Autowired
    DirectmessageService directmessageService;
    @Autowired
    DirectmessageRepository directmessageRepository;
    @Autowired
    EntityManager em;

    @Test // 메시지 저장
//    @Rollback(value = false)
    public void saveDirectMessage() throws Exception {
        // Given
        Profile profile1 = new Profile("1", "얄옹심", "얄옹심ImageUrl", "얄옹심ImagePath", "세계 정복");
        Profile profile2 = new Profile("2", "지민씨", "지민씨ImageUrl", "지민씨ImagePath", "우주 정복");

        // messageid, sender, recipient, localdatetime, mcheck, mtitle, mcontents
        Directmessage directMessage1 = new Directmessage(profile1.getUid(), profile2.getUid(), LocalDateTime.now(), 0, "자니?", "잘 지내...?");

        directMessage1.setProfiles(profile1);
        directMessage1.setProfiles(profile2);

        // When
        em.persist(profile1);
        em.persist(profile2);
        directmessageService.saveDirectmessage(directMessage1);
        Directmessage message1 = directmessageService.findDirectmessage("messageid1");
    //    List<Directmessage> message2 = directmessageService.findAllDirectMessagesBySender("1");

        // Then
        em.flush();
        Assertions.assertThat(directMessage1).isNotNull(); // 메시지가 존재해야 함

    }

    @Test // 메시지 조회
    public void findDirectMessagesBySenderAndReceiver() throws Exception {
        // Given
        Profile profile3 = new Profile("3", "짱짱다", "짱짱다ImageUrl", "짱짱다ImagePath", "좀비고 조아...");
        Profile profile4 = new Profile("4", "가지군", "가지군ImageUrl", "가지군ImagePath", "보라색 조아");

        // messageid, sender, recipient, localdatetime, mcheck, mtitle, mcontents
        Directmessage directMessage2 = new Directmessage(profile3.getUid(), profile4.getUid(), LocalDateTime.now(), 0, "배고팡", "마라탕 먹구 시퍼");
        Directmessage directMessage3 = new Directmessage(profile4.getUid(), profile3.getUid(), LocalDateTime.now(), 0, "졸령", "잠 좀 자구 시퍼");

        // When
        em.persist(profile3);
        em.persist(profile4);
        directmessageService.saveDirectmessage(directMessage2);
        directmessageService.saveDirectmessage(directMessage3);
        Directmessage message3 = directmessageService.findDirectmessage("messageid2");
        List<Directmessage> message4 = directmessageService.findAllDirectMessagesBySender("3");
        List<Directmessage> message5 = directmessageService.findAllDirectMessagesByRecipient("4");

        // Then
        Assertions.assertThat(directMessage2).isNotNull(); // 메시지가 존재해야 함
        Assertions.assertThat(directMessage3).isNotNull(); // 메시지가 존재해야 함
    }

    @Test // 메시지 삭제
    public void deleteDirectMessage() throws Exception {
        // Given
        Profile profile5 = new Profile("5", "감자하나", "감자하나ImageUrl", "감자하나ImagePath", "감자도리");
        Profile profile6 = new Profile("6", "감자둘", "감자둘ImageUrl", "감자둘ImagePath", "감자도리링");

        Directmessage directMessage4 = new Directmessage(profile5.getUid(), profile6.getUid(), LocalDateTime.now(), 0, "닭갈비", "구워줘");
        Directmessage directMessage5 = new Directmessage(profile6.getUid(), profile5.getUid(), LocalDateTime.now(), 0, "감자밭", "먹으러 갈랭?");

        // When
        em.persist(profile5);
        em.persist(profile6);
        directmessageService.saveDirectmessage(directMessage4);
        directmessageService.saveDirectmessage(directMessage5);

        // 삭제
        directmessageService.deleteDirectmessage(directMessage4);
        directmessageService.deleteDirectmessage(directMessage5);

        // 삭제 후 메시지 조회, 이 과정을 안 해주면 then 실행 불가, 삭제 후 널값 확인하기 위해선 꼭 데이터베이스 조회를 먼저 해줘야 함
        Directmessage deletedMessage4 = directmessageService.findDirectmessage("messageid4");
        Directmessage deletedMessage5 = directmessageService.findDirectmessage("messageid5");

        // Then
        Assertions.assertThat(deletedMessage4).isNull(); // 삭제된 메시지는 null이어야 함
        Assertions.assertThat(deletedMessage5).isNull(); // 삭제된 메시지는 null이어야 함
    }

    @Test // 보낸이(sender)에 대한 모든 쪽지 조회
    public void findAllDirectMessagesBySender() throws Exception {
        // Given
        Profile profile7 = new Profile("7", "감자셋", "감자셋ImageUrl", "감자셋ImagePath", "감자셋둘하나");
        Profile profile8 = new Profile("8", "감자넷", "감자넷ImageUrl", "감자넷ImagePath", "감자넷다섯여섯일곱");
        Profile test1 = new Profile("test1Uid", "test1", "test1ImageUrl", "test1ImagePath", "test1 메세지");
        Profile test2 = new Profile("test2Uid", "test2", "test2ImageUrl", "test2ImagePath", "test2 메세지");

        Directmessage directMessage6 = new Directmessage(profile7.getUid(), profile8.getUid(), LocalDateTime.now(), 0, "감자넷아", "감자나 먹어");
        Directmessage directMessage7 = new Directmessage(profile7.getUid(), profile8.getUid(), LocalDateTime.now(), 0, "쪽지 좀 봐주겠니?", "쪽지를 왜 안 봐");
        Directmessage directMessage8 = new Directmessage(test1.getUid(), test2.getUid(), LocalDateTime.now(), 1, "우힝힝", "밥 줘");

        // When
        em.persist(profile7);
        em.persist(profile8);
        em.persist(test1);
        em.persist(test2);
        directmessageService.saveDirectmessage(directMessage6);
        directmessageService.saveDirectmessage(directMessage7);
        directmessageService.saveDirectmessage(directMessage8);
        List<Directmessage> message6 = directmessageService.findAllDirectMessagesBySender("7");

        // Then
        Assertions.assertThat(directMessage6).isNotNull(); // 메시지가 존재해야 함
        Assertions.assertThat(directMessage7).isNotNull(); // 메시지가 존재해야 함
        Assertions.assertThat(directMessage8).isNotNull(); // 메시지가 존재해야 함
    }

    @Test // 수신자(recipient)에 대한 모든 쪽지 조회
    public void findAllDirectMessagesByRecipient() throws Exception {
        // Given
        Profile profile9 = new Profile("9", "감자군", "감자군ImageUrl", "감자군ImagePath", "고구마양...");
        Profile profile10 = new Profile("10", "고구마양", "고구마양ImageUrl", "고구마양ImagePath", "감자 시러");


        Directmessage directMessage9 = new Directmessage(profile9.getUid(), profile10.getUid(), LocalDateTime.now(), 1, "고구마양", "너를 향한 나의 마음을 알아조");
        Directmessage directMessage10 = new Directmessage(profile9.getUid(), profile10.getUid(), LocalDateTime.now(), 0, "고구마양 외 않바?", "쪽지 좀 읽어줘");
        Directmessage directMessage11 = new Directmessage(profile10.getUid(), profile9.getUid(), LocalDateTime.now(), 0, "보이면 안 됨", "보이면 안 됨");


        // When
        em.persist(profile9);
        em.persist(profile10);
        directmessageService.saveDirectmessage(directMessage9);
        directmessageService.saveDirectmessage(directMessage10);
        directmessageService.saveDirectmessage(directMessage11);
        List<Directmessage> message7 = directmessageService.findAllDirectMessagesByRecipient("10");

        // Then
        Assertions.assertThat(directMessage9).isNotNull(); // 메시지가 존재해야 함
        Assertions.assertThat(directMessage10).isNotNull(); // 메시지가 존재해야 함
        Assertions.assertThat(directMessage11).isNotNull(); // 메시지가 존재해야 함
    }



}

