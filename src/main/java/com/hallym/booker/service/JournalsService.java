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
    public void saveJournals(Journals journals) {
        journalsRepository.save(journals);
    }

    @Transactional
    public void updateJournals(String jid, String ptitle, String pcontents, String pimageUrl, String pimagePath) {
        Journals findJournal = journalsRepository.findOne(jid);
        findJournal.change(ptitle, pcontents, pimageUrl, pimagePath);
        //journalsRepository.update(journals);
    }

    public List<Journals> findItems() {
        return journalsRepository.findAll();
    }

    public Journals findOne(String uid) {
        return journalsRepository.findOne(uid);
    }

    @Transactional
    public void deleteJournals(Journals journals) {
        journalsRepository.delete(journals);
    }
}
