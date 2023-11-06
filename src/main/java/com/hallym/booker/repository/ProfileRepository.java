package com.hallym.booker.repository;

import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
//영속성 컨텍스트 주입
@RequiredArgsConstructor
public class ProfileRepository {
    private final EntityManager em;

    //프로필 생성
    public void generate(Profile profile){
            em.persist(profile);
    }

    //프로필 수정
    /*public void update(Profile profile){
        em.merge(profile);
    }*/

    //프로필 찾기
    public Profile findOne(String uid){
        return em.find(Profile.class, uid);
    }

    //프로필 닉네임으로 찾기
    public List<Profile> findByNickname(String nickname){
        return em.createQuery("select m from Profile m where m.nickname like concat('%', :nickname, '%')", Profile.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    //프로필 삭제
    public void deleteById(Profile profile){
        em.remove(profile);
    }
}
