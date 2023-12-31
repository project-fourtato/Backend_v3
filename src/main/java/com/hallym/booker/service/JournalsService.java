package com.hallym.booker.service;

import com.hallym.booker.domain.Journals;
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
public class JournalsService {

    private final JournalsRepository journalsRepository;
    private  final FollowRepository followRepository;

    @Transactional
    public void saveJournals(Journals journals) {
        journalsRepository.save(journals);
    }

    @Transactional
    public void updateJournals(String jid, String ptitle, String pcontents, String pimageUrl, String pimagePath) {
        Optional<Journals> findJournal = journalsRepository.findById(jid);

        findJournal.ifPresent(journals -> journals.change(ptitle, pcontents, pimageUrl, pimagePath));
        //journalsRepository.update(journals);
    }

    public List<Journals> findAllJournalsByUserbid(String userbid) {
        return journalsRepository.findByBooks_Userbid(userbid);
    }

    public Journals findOne(String uid) {
        Optional<Journals> findJournal = journalsRepository.findById(uid);

        return findJournal.orElse(null);
    }

    // 11/12 추가, fromUserId가 팔로잉 중인 toUserId의 모든 독서록 조회
    public List<Journals> findLatestJournalByUser(String userbid) {
        return journalsRepository.findAllByBooks_UserbidOrderByPdatetimeDesc(userbid);
    }

    @Transactional
    public void deleteJournals(Journals journals) {
        if(journals != null) {
            journalsRepository.delete(journals);
        }
    }
}