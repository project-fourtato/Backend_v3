package com.hallym.booker.repository;

import com.hallym.booker.domain.Journals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalsRepository extends JpaRepository<Journals, String> {

    public List<Journals> findByBooks_Userbid(String userbid);

    // fromUserId가 팔로잉 중인 toUserId의 모든 독서록 조회
    //@Query(value = "select * from journals where userbid = ?1 order by pdatetime desc", nativeQuery = true)

    // 11/12 추가, 팔로잉 하는 유저들의 최신 독서록 목록 조회에 사용
    List<Journals> findAllByBooks_UserbidOrderByPdatetimeDesc(String userbid);

}