package com.hallym.booker.service;

import com.hallym.booker.domain.Interests;
import com.hallym.booker.repository.InterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestsService {
    private final InterestsRepository interestsRepository;

    // 관심사 저장
    @Transactional
    public void saveInterests(Interests interests) {
        interestsRepository.saveInterests(interests);
    }

    // 관심사 조회
    public Interests findInterests(String uid) {
        return interestsRepository.findInterests(uid);
    }

    // 관심사 수정
    @Transactional
    public void updateInterests(String uid, String uinterest1, String uinterest2, String uinterest3, String uinterest4, String uinterest5) {
        Interests interests = interestsRepository.findInterests(uid);
        interests.change(uinterest1, uinterest2, uinterest3, uinterest4, uinterest5);
        //return interestsRepository.updateInterests(interests);
    }

    // 관심사 삭제
    @Transactional
    public void deleteInterests(Interests interests) {
        interestsRepository.deleteInterests(interests);
    }

    // 특정 프로필에 대한 모든 관심사 조회
    public List<Interests> findAllInterestsByProfile(String profileUid) {
        return interestsRepository.findAllInterestsByProfile(profileUid);
    }


}
