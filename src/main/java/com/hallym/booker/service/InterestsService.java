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
    public Interests updateInterests(Interests interests) {
        return interestsRepository.updateInterests(interests);
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
