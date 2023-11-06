package com.hallym.booker.service;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.LoginRepository;
import com.hallym.booker.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) //@Autowird, @MockBean에 해당되는 것들에만 application context 로딩
@SpringBootTest //application context 전부 로딩
@Transactional
public class ProfileServiceTest {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileService profileService;
    @Test
//    @Rollback(value = false) //DB에 저장시킴
    public void join() throws Exception{
        //given
        Profile profile = new Profile("130ekcn1r","nick","useriamgeUrl","userimagePath","usermessage");
        //when
        String saveUid = profileService.join(profile);
        //then
        assertEquals(profile, profileRepository.findOne(saveUid));
    }

    @Test
    public void findOne() throws Exception{
        //given
        Profile profile = new Profile("130ekcr","nick","useriamgeUrl","userimagePath","usermessage");
        profileService.join(profile);
        //when
        //then
        assertEquals(profile.getNickname(), profileRepository.findOne("130ekcr").getNickname());

    }

    @Test
    public void fndByNickname() throws Exception{
        //given
        Profile profile = new Profile("130ekcr","nick","useriamgeUrl","userimagePath","usermessage");
        profileService.join(profile);
        Profile profile1 = new Profile("130ekcr33","nick","useriamgeUrl","userimagePath","usermessage");
        profileService.join(profile1);

        //when
        int c = profileRepository.findByNickname("nick").size();
        //then
        assertEquals(2, c);
    }

    @Test
//    @Rollback(value = false) //DB에 저장시킴
    public void deleteOne() throws Exception{
        //given
        Profile profile = new Profile("130ekcr","nick","useriamgeUrl","userimagePath","usermessage");
        profileService.join(profile);
        //when
        String deleteProfile = profileService.deleteOne(profile);
        Profile findProfile = profileService.findOne(profile.getUid());
        //then
        Assertions.assertThat(findProfile).isNull();
    }

    @Test
    public void updateProfile() throws Exception {
        //given
        Profile profile = new Profile("130ekcn1r","nick","useriamgeUrl","userimagePath","usermessage");
        String saveUid = profileService.join(profile);

        //when
        profileService.updateProfile("130ekcn1r", "useriamgeUrl", "userimagePath", "호러 좋아해요");
//        Profile updateProfile = new Profile("130ekcn1r","hihi","useriamgeUrl","userimagePath","호러 좋아해요");
//        profileService.updateProfile(updateProfile);
        //then
        assertEquals("호러 좋아해요", profileRepository.findOne(saveUid).getUsermessage());
    }
}