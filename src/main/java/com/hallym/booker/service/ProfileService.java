package com.hallym.booker.service;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.LoginRepository;
import com.hallym.booker.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Profile findByNickname(String nickname){
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
    public void updateProfile(Profile p){
        Profile updateOne = new Profile(p.getUid(),p.getNickname(),p.getUseriamgeUrl(),p.getUserimagePath(),p.getUsermessage());
        profileRepository.update(updateOne);
    }
}
