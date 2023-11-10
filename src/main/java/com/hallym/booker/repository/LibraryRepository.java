package com.hallym.booker.repository;

import com.hallym.booker.domain.LibraryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LibraryRepository {
    private final EntityManager em;

    public List<LibraryList> findByName(String libName) {
        return em.createQuery("select f from LibraryList f where f.libName like concat('%', :libName, '%')", LibraryList.class)
                .setParameter("libName", libName)
                .getResultList();
    }
}
