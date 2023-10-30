package com.hallym.booker.service;

import com.hallym.booker.domain.Followers;
import com.hallym.booker.domain.Followings;
import com.hallym.booker.repository.FollowingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowingsService {
    private final FollowingsRepository followingsRepository;

    @Transactional
    public void saveItem(Followings followings) {
        followingsRepository.save(followings);
    }

    public List<Followings> findItems() {
        return followingsRepository.findAll();
    }

    @Transactional
    public void deleteFollowers(Followings followings) {
        followingsRepository.deleteById(followings);
    }
}
