package com.hallym.booker.service;

import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    /**
     *  프로필 등록
     */
    @Transactional //변경
    public String join(Profile profile){
        //중복 회원 검증 구현X
        profileRepository.generate(profile);
        return profile.getUid();
    }

    /**
     * 회원 찾기
     */
    public Profile findOne(String uid){
        return profileRepository.findOne(uid);
    }
    /**
     * 회원 닉네임으로 찾기
     */
    public List<Profile> findByNickname(String nickname){
        return profileRepository.findByNickname(nickname);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public String deleteOne(Profile profile){
        profileRepository.deleteById(profile);
        return profile.getUid();
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateProfile(String uid, String userimageUrl, String userimageName, String usermessage) {
        Profile findProfile = profileRepository.findOne(uid);
        System.out.println(findProfile + usermessage);
        if(userimageName == null && userimageUrl == null) {
            findProfile.change("https://storage.googleapis.com/booker-v3/basis-profile.png", "basis-profile", usermessage);
        } else {
            System.out.println(usermessage);
            findProfile.change(userimageUrl, userimageName, usermessage);
        }
    }

    /**
     * 닉네임 중복 검사
     */
    public Boolean checkNickname(String nickname){
        List<Profile> findMembers = profileRepository.findByNickname(nickname);
        if(!findMembers.isEmpty()){
            return false; //회원 존재
        }
        else {
            return true; //회원 없음
        }
    }

}
