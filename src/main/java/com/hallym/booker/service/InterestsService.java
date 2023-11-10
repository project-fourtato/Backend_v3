package com.hallym.booker.service;

import com.hallym.booker.domain.Interests;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.repository.InterestsRepository;
import com.hallym.booker.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestsService {
    private final InterestsRepository interestsRepository;
    private final ProfileRepository profileRepository;

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

    /**
     * 관심사가 동일한 유저 조회
     */
    public List<Profile> sameInterests(Interests interests){
        int mecnt = 0;
        //내가 관심사가 몇 개인지 탐색
        if(interests.getUinterest3()==null) mecnt = 2;
        else if(interests.getUinterest4()==null) mecnt = 3;
        else if(interests.getUinterest5()==null) mecnt = 4;
        List<Interests> interestsList = interestsRepository.findAllInterests(interests.getUid());
        List<Profile> result = new LinkedList<>();
        int cnt = 0;
        int tempcnt = 0;
        List<Interests> temp = new LinkedList<>();
        //관심사가 2개 이상 동일한 유저 탐색
        for(int i=0;i<interestsList.size();i++){ //상대방들
            //null인 거 제외

            if(interestsList.get(i).getUinterest3()==null) tempcnt = 2;
            else if(interestsList.get(i).getUinterest4()==null) tempcnt = 3;
            else if(interestsList.get(i).getUinterest5()==null) tempcnt = 4;
            else tempcnt=5;
            for(int j=1;j<=tempcnt;j++){
                String a = null;
                switch (j) { //상대 관심사들이
                    case 1: a = interestsList.get(i).getUinterest1();break;
                    case 2: a = interestsList.get(i).getUinterest2();break;
                    case 3: a = interestsList.get(i).getUinterest3();break;
                    case 4: a = interestsList.get(i).getUinterest4();break;
                    case 5: a = interestsList.get(i).getUinterest5();break;
                }
                for(int z=1;z<=mecnt;z++){
                    String b = null;
                    switch (z) { //나의 관심사와 동일한지 비교
                        case 1: b = interests.getUinterest1();break;
                        case 2: b = interests.getUinterest2();break;
                        case 3: b = interests.getUinterest3();break;
                        case 4: b = interests.getUinterest4();break;
                        case 5: b = interests.getUinterest5();break;
                    }
                    if(a.equals(b)) {
                        cnt++;
                    }
                }
                if(cnt >=2) { //2개 이상 관심사가 동일하면 프로필 추가
                    result.add(profileRepository.findOne(interestsList.get(i).getUid()));
                    cnt=0;
                    break;
                }
            }
        }
        return result;
    }
}
