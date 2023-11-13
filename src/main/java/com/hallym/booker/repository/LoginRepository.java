package com.hallym.booker.repository;

import com.hallym.booker.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, String> {


    // 로그인 유효성 검사를 위해서만 쓰임
    @Query(value = "select * from login where login_uid = ?1", nativeQuery = true)
    List<Login> findByUid(String uid);

    //회원 로그인
    @Query(value = "select * from login where login_uid = ?1 and pw = ?2", nativeQuery = true)
    Optional<Login> loginOne(String uid, String pw);

    //회원 수정 폼
    @Query(value = "select * from login where login_uid = ?1", nativeQuery = true)
    Login findOne(String uid);

    // 회원 삭제
    Integer deleteByUid(String uid);

    // foreign key constraint fails 에러를 위한 생쿼리문
    @Modifying
    @Query(value = "SET foreign_key_checks = 0", nativeQuery = true)
    void disableForeignKeyChecks();

    @Modifying
    @Query(value = "DELETE FROM :login", nativeQuery = true)
    void deleteTableWithForeignKeyChecks(Login login);

    @Modifying
    @Query(value = "SET foreign_key_checks = 1", nativeQuery = true)
    void enableForeignKeyChecks();

}