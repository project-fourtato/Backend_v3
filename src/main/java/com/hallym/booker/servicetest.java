package com.hallym.booker;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Login;
import com.hallym.booker.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class servicetest {

    //@Autowired //스프링이 스프링빈에 등록되어 있는 memberRepository를 인젝션 해줌
    private final test memberRepository; //변경할 일이 없어서 final

    //@Autowired //생성자 하나만 존재하는 경우에는 스프링이 알아서 인젝션 해줌

    //회원가입
    @Transactional
    public String join(Profile member) {
        memberRepository.save(member);
        return member.getNickname();
    }

    @Transactional
    public String loginjoin(Login loginmember) {
        memberRepository.loginsave(loginmember);
        return loginmember.getUid();
    }

    @Transactional
    public String booksjoin(Books booksmember) {
        memberRepository.Bookssave(booksmember);
        return booksmember.getBooksUid();
    }

    public List<Profile> findMembers() {
        return memberRepository.findAll();
    }

    public Profile findOne(String memberId) {
        return memberRepository.findOne(memberId);
    }
}
