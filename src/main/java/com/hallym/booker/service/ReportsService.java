package com.hallym.booker.service;

import com.hallym.booker.domain.Profile;
import com.hallym.booker.domain.Reports;
import com.hallym.booker.domain.Rtype;
import com.hallym.booker.repository.ProfileRepository;
import com.hallym.booker.repository.ReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportsService {
    private final ReportsRepository reportsRepository;

    /**
     *  리폿 등록
     */
    @Transactional //변경
    public String join(Reports reports){
        //중복 회원 검증 구현X
        reportsRepository.save(reports);
        return reports.getUid();
    }

    /**
     * 리폿 찾기
     */
    public Reports findOne(String uid){
        return reportsRepository.findOne(uid);
    }
    /**
     * 리폿 rtype으로 검색 후 목록으로 찾기
     */
    public List<Reports> findByRtype(Rtype rtype){
        return reportsRepository.findByRtype(rtype);
    }

    /**
     * 리폿 삭제
     */
    @Transactional
    public String deleteOne(Reports reports){
        reportsRepository.deleteById(reports);
        return reports.getUid();
    }
}
