package com.hallym.booker.service;

import com.hallym.booker.domain.Interests;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.InterestsRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InterestServiceTest {


    @Autowired
    InterestsService interestsService;
    @Autowired
    InterestsRepository interestsRepository;
    @Autowired
    EntityManager em;

    @Test // 관심사 추가
    public void saveInterests() throws Exception {
        // Given
        Profile profile1 = new Profile("yarong1234", "yarong", "userimageUrl", "userimagePath", "나는 돌");

        Interests interests = new Interests("yarong1234", "Interest1", "Interest2", "Interest3", "Interest4", "Interest5");
        interests.setProfile(profile1); // Interests와 Profile을 연결

        // When
        em.persist(profile1);
        interestsService.saveInterests(interests);
        List<Interests> items = interestsService.findAllInterestsByProfile("yarong1234"); // 해당 Profile의 관심사 목록을 가져옴

        // Then
        for (Interests interest : items) {
            Assertions.assertThat(interest.getUid()).isNotNull(); // 관심사가 널값이 아니어야 함
        }
    }

    @Test // 해당 유저가 가지고 있는 관심사 조회
    public void findAllInterestsByProfile() {
        // Given
        Profile profile1 = new Profile("yarong1234", "yarong", "userimageUrl", "userimagePath", "나는 돌");

        Interests interests1 = new Interests("yarong1234", "Interest1", "Interest2", "Interest3", "Interest4", "Interest5");
        interests1.setProfile(profile1); // Interests와 Profile을 연결

        // When
        em.persist(profile1);
        interestsService.saveInterests(interests1);
        List<Interests> interestsList = interestsService.findAllInterestsByProfile("yarong1234");

        // Then
        for (Interests interests : interestsList) {
            Assertions.assertThat(interests.getUid()).isNotNull(); // 관심사가 널값이 아니어야 함
            Assertions.assertThat(interests.getUinterest1()).isNotNull();
            Assertions.assertThat(interests.getUinterest2()).isNotNull();
            Assertions.assertThat(interests.getUinterest3()).isNotNull();
            Assertions.assertThat(interests.getUinterest4()).isNotNull();
            Assertions.assertThat(interests.getUinterest5()).isNotNull();
        }
    }

    @Test // 관심사 수정
    public void updateInterests() {
        // Given
        Profile profile = new Profile("5678", "yarong", "userimageUrl", "userimagePath", "나는 돌");

        Interests interests = new Interests("5678", "Interest1", "Interest2", "Interest3", "Interest4", "Interest5");
        interests.setProfile(profile); // Interests와 Profile을 연결

        //Interests interestsUpdate = new Interests("1234", "수정1", "수정2", "수정3", "수정4", "수정5");
        //interestsUpdate.setProfile(profile); // Interests와 Profile을 연결

        // When
        em.persist(profile);
        interestsService.saveInterests(interests); // 첫 저장

        interestsService.updateInterests("5678", "Interest1", "Interest2", "Interest3", "Interest4", "관심사5");
        //Interests interestsFirst = interestsService.findInterests(interests.getUid());

        //interestsService.updateInterests(interestsUpdate); // 수정 저장
        //Interests interestsSecond = interestsService.findInterests(interestsUpdate.getUid());

        // Then

        // 관심사 업데이트 되었는지 확인
        //Assertions.assertThat(interestsSecond).isNotNull(); // 업데이트 된 관심사가 null 이 아닌지 확인

        // 각 관심사 필드 확인해서 업데이트 검증
        Assertions.assertThat("관심사5").isEqualTo(interestsRepository.findInterests("5678").getUinterest5());
        /*Assertions.assertThat(interestsSecond.getUinterest2()).isEqualTo("수정2");
        Assertions.assertThat(interestsSecond.getUinterest3()).isEqualTo("수정3");
        Assertions.assertThat(interestsSecond.getUinterest4()).isEqualTo("수정4");
        Assertions.assertThat(interestsSecond.getUinterest5()).isEqualTo("수정5");*/
        em.flush();
    }

    @Test // 관심사 삭제
    public void deleteInterests() throws Exception {
        // Given
        Profile profile = new Profile("1234", "yarong", "userimageUrl", "userimagePath", "나는 돌");

        Interests interests = new Interests("1234", "Interest1", "Interest2", "Interest3", "Interest4", "Interest5");
        interests.setProfile(profile); // Interests와 Profile을 연결


        // When
        em.persist(profile);
        interestsService.saveInterests(interests); // 관심사 정보를 저장
        List<Interests> items = interestsService.findAllInterestsByProfile("1234"); // 해당 Profile의 관심사 목록을 가져옴

        for (Interests interest : items) {
            interestsService.deleteInterests(interest); // 관심사 삭제
        }

        // 삭제된 관심사 찾아보기
        Interests deletedInterests = interestsService.findInterests("1234");

        // Then
        Assertions.assertThat(deletedInterests).isNull(); // 삭제된 관심사 정보는 null이어야 함
    }


}