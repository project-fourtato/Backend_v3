package com.hallym.booker.repository;

import com.hallym.booker.domain.Journals;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalsRepository extends JpaRepository<Journals, String> {

    public List<Journals> findByBooks_Userbid(String userbid);
}
