package com.hallym.booker.service;

import com.hallym.booker.domain.Followings;
import com.hallym.booker.domain.Journals;
import com.hallym.booker.repository.FollowingsRepository;
import com.hallym.booker.repository.JournalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JournalsService {

    private final JournalsRepository journalsRepository;

    @Transactional
    public void saveItem(Journals journals) {
        journalsRepository.save(journals);
    }

    public List<Journals> findItems() {
        return journalsRepository.findAll();
    }

    public Journals findOne(String uid) {
        return journalsRepository.findOne(uid);
    }

    @Transactional
    public void deleteJournals(Journals journals) {
        journalsRepository.deleteById(journals);
    }
}
