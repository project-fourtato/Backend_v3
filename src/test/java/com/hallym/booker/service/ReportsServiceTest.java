package com.hallym.booker.service;

import com.hallym.booker.domain.Reports;
import com.hallym.booker.domain.Rtype;
import com.hallym.booker.repository.ReportsRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) //@Autowird, @MockBean에 해당되는 것들에만 application context 로딩
@SpringBootTest //application context 전부 로딩
@Transactional
public class ReportsServiceTest {
    @Autowired
    ReportsRepository reportsRepository;

    @Autowired
    ReportsService reportsService;
    @Test
    public void join() {
        //given
        Reports reports = new Reports("130ekcr",Rtype.BUG,"rimageUrl","rimagePath","rcontents",LocalDateTime.now(),"rnickname");
        //when
        String saveUid = reportsService.join(reports);
        //then
        assertEquals(reports, reportsRepository.findOne(saveUid));
    }

    @Test
    public void findOne() {
        //given
        Reports reports = new Reports("130ekcr",Rtype.BUG,"rimageUrl","rimagePath","rcontents",LocalDateTime.now(),"rnickname");
        //when
        String saveUid = reportsService.join(reports);
        //then
        assertEquals(reports.getRnickname(), reportsRepository.findOne("130ekcr").getRnickname());
    }

    @Test
    public void findByRtype() {
        //given
        Reports reports1 = new Reports("222ekcr",Rtype.USER,"rimageUrl","rimagePath","rcontents",LocalDateTime.now(),"rnickname");
        Reports reports2 = new Reports("130ekcr",Rtype.BUG,"rimageUrl","rimagePath","rcontents",LocalDateTime.now(),"rnickname");
        //when
        String saveUid1 = reportsService.join(reports1);
        String saveUid2 = reportsService.join(reports2);

        List<Reports> reportsList = reportsRepository.findByRtype(Rtype.USER);
        //then
        assertEquals(1, reportsList.size());
    }

    @Test
    public void deleteOne() {
        //given
        Reports reports = new Reports("130ekcr",Rtype.BUG,"rimageUrl","rimagePath","rcontents",LocalDateTime.now(),"rnickname");
        //when
        reportsService.join(reports);
        String deleteReports = reportsService.deleteOne(reports);
        Reports findReports = reportsService.findOne(reports.getUid());
        //then
        Assertions.assertThat(findReports).isNull();
    }
}