package com.hallym.booker.service;

import com.hallym.booker.domain.Followers;
import com.hallym.booker.repository.FollowersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowersService {

    private final FollowersRepository followersRepository;

    @Transactional
    public void saveItem(Followers followers) {
        followersRepository.save(followers);
    }

    public List<Followers> findItems() {
        return followersRepository.findAll();
    }

    @Transactional
    public void deleteFollowers(Followers followers) {
        followersRepository.delete(followers);
    }
}
