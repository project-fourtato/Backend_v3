package com.hallym.booker.service;

import com.hallym.booker.domain.Follow;
import com.hallym.booker.repository.FollowRepository;
import com.hallym.booker.repository.JournalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final JournalsRepository journalsRepository;


    // 팔로잉 & 팔로워 추가
    @Transactional
    public void saveFollow(Follow follow) {
        followRepository.save(follow);

    }

    // 팔로잉 조회, 팔로우 삭제할 때 사용
    public Follow findOneTo(String fromUserId, String toUserId){
        Optional<Follow> findFollow = followRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);

        return findFollow.orElse(null);
    }

    // 팔로워 조회, 아직 사용할 일 x
    public Follow findOneFrom(String toUserId, String fromUserId){
        Optional<Follow> findFollow = followRepository.findByFromUserIdAndToUserId(toUserId, fromUserId);

        return findFollow.orElse(null);
    }

    // fromUserId가 팔로잉 하는 목록 조회 (전체)
    public List<Follow> findAllTo(String fromUserId) {
        return followRepository.findAllByToUserId(fromUserId);
    }

    // toUserId를 팔로워 하는 수 조회 (전체), 아직 사용할 일 x
    public List<Follow> findAllFrom(String toUserId) {
        return followRepository.findAllByFromUserId(toUserId);
    }

    // fromUserId가 팔로잉 하는 수 조회 (전체)
    public Long countByFromUserId(String fromUserId) {
        return followRepository.countByFromUserId(fromUserId);
    }

    // toUserId를 팔로워 하는 수 조회 (전체)
    public Long countByToUserId(String toUserId) { return followRepository.countByToUserId(toUserId);}

    // 팔로잉 & 팔로워 삭제
    @Transactional
    public void deleteFollow(Follow follow) {
        followRepository.delete(follow);
    }

}