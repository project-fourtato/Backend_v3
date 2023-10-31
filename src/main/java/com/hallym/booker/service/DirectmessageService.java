package com.hallym.booker.service;

import com.hallym.booker.domain.Directmessage;
import com.hallym.booker.repository.DirectmessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectmessageService {
    private final DirectmessageRepository directmessageRepository;

    // 쪽지 저장
    @Transactional
    public void saveDirectmessage(Directmessage directmessage) {
        directmessageRepository.saveDirectmessage(directmessage);
    }

    // 쪽지 조회
    public Directmessage findDirectmessage(String messageid) {
        return directmessageRepository.findDirectmessage(messageid);
    }

    // 쪽지 삭제
    @Transactional
    public void deleteDirectmessage(Directmessage directmessage) {
        directmessageRepository.deleteDirectmessage(directmessage);
    }

    // 특정 보낸이(sender)에 대한 모든 쪽지 조회
    public List<Directmessage> findAllDirectMessagesBySender(String senderUid) {
        return directmessageRepository.findAllDirectmessagesBySender(senderUid);
    }

    // 특정 수신자(recipient)에 대한 모든 쪽지 조회
    public List<Directmessage> findAllDirectMessagesByRecipient(String recipientUid) {
        return directmessageRepository.findAllDirectMessagesByRecipient(recipientUid);
    }

}
