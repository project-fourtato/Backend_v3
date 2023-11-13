package com.hallym.booker.repository;

import com.hallym.booker.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> { // 주키 타입 적기

    // 팔로잉 조회, 한 사람만 찾고 싶을 때 사용함. fromuserid 관련된 건 생쿼리 사용해야 함!!, 뒤에 nativeQuery = true 붙여주기
    @Query(value = "select * from follow where from_user_id = ?1 and to_user_id = ?2", nativeQuery = true)
    Optional<Follow> findByFromUserIdAndToUserId(String fromUserId, String toUserId);

    /*  팔로워 조회, 쓸 일 x
      Optional<Follow> findByToUserId(String toUserId);*/

    // fromUserId가 팔로잉 하는 목록 조회 (전체)
    @Query(value = "select * from follow where from_user_id = ?1", nativeQuery = true)
    List<Follow> findAllByToUserId(String fromUserId);

    // toUserId를 팔로워 하는 수 조회 (전체), 아직 사용할 일 x
    @Query(value = "select * from follow where to_user_id = ?1", nativeQuery = true)
    List<Follow> findAllByFromUserId(String toUserId);

    // fromUserId가 팔로잉 하는 수 조회 (전체)
    @Query(value = "select count() from follow where from_user_id = ?1", nativeQuery = true)
    Long countByFromUserId(String fromUserId);

    // toUserId를 팔로워 하는 수 조회 (전체)
    @Query(value = "select count() from follow where to_user_id = ?1", nativeQuery = true)
    Long countByToUserId(String toUserId);

}