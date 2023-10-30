package com.hallym.booker.service;

import com.hallym.booker.domain.Followers;
import com.hallym.booker.domain.Followings;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.FollowersRepository;
import com.hallym.booker.repository.FollowingsRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //junit 실행할 때, 스프링이랑 같이 엮어서 실행할래
@SpringBootTest //springboot 띄운 상태로 테스트
@Transactional //이게 test에 있음 기본적으로 rollback
public class FollowingsServiceTest {

    @Autowired
    FollowingsService followingsService;
    @Autowired
    FollowingsRepository followingsRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 모든_팔로잉_추가_후_찾기() throws Exception {
        //given
        Profile profileA = new Profile("sdfikewqjopscdvk", "감자", "efsc.wvdwrb.sdv", "dsfcwev/svwsvf/vsds", "해리포터 좋아해요.");
        Profile profileB = new Profile("weuidsfwf", "고구마", "wj.wvoijw.ewfok", "wvgds/wgafqe/qwr/eg", "판타지 좋아해용"); // B가 A를 팔로우

        Followings followings = new Followings("sdfikewqjopscdvk", profileA.getUid()); //B의 팔로잉에 A가 들어옴
        followings.setProfile(profileB);

        //when
        em.persist(profileA);
        em.persist(profileB);
        followingsService.saveItem(followings);
        List<Followings> items = followingsService.findItems();

        //then
        em.flush();
        Assertions.assertThat(items.size()).isEqualTo(1);
    }

}