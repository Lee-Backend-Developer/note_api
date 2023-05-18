package com.note.api.service;

import com.note.api.entity.Member;
import com.note.api.exception.MemberDuplication;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.MemberRepository;
import com.note.api.request.member.MemberLogin;
import com.note.api.request.member.MemberPwdChange;
import com.note.api.request.member.MemberRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.deleteAll();
    }

    @DisplayName("회원가입이 되어야함")
    @Test
    void register_o() {
        // given
        MemberRegister request = MemberRegister.builder()
                .loginId("test")
                .password("1234")
                .build();

        // when
        memberService.singUp(request);

        // then
        Member findMember = memberRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword())
                .orElseThrow(MemberNotFound::new);
        assertEquals(findMember.getLoginId(), request.getLoginId());
        assertEquals(findMember.getPassword(), request.getPassword());
    }

    @DisplayName("이미 회원가입 한 회원이 있을경우 예외처리가 되어야함")
    @Test
    void register_x() {
        // given
        Member createMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();
        memberRepository.save(createMember);

        MemberRegister request = MemberRegister.builder()
                .loginId("test")
                .password("1234")
                .build();

        // expected
        assertThrows(MemberDuplication.class, () -> {
            memberService.singUp(request);
        });
    }

    @DisplayName("로그인이 되어야함")
    @Test
    void login_o() {
        // given
        Member createMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();
        memberRepository.save(createMember);

        MemberLogin request = MemberLogin.builder()
                .loginId("test")
                .password("1234")
                .build();


        // when
        Member requestMember = memberService.singIn(request);

        // then
        assertEquals("test", requestMember.getLoginId());
    }

    @DisplayName("회원가입 안된 회원을 로그인할 경우 예외처리 되어야함")
    @Test
    void login_x() {
        // given
        MemberLogin request = MemberLogin.builder()
                .loginId("test")
                .password("1234")
                .build();

        // expected
        assertThrows(MemberNotFound.class, () -> {
            memberService.singIn(request);
        });
    }

    @DisplayName("회원이 비밀번호 변경이 되어야함")
    @Test
    void change_password_o() {
        // given
        Member save = memberRepository.save(Member.builder()
                .loginId("test")
                .password("1234")
                .build());

        MemberPwdChange requestPwd = MemberPwdChange.builder()
                .currentPwd(save.getPassword())
                .changePwd("12345")
                .build();

        // when
        memberService.changePwd(save.getMemberId(), requestPwd);


        // then
        Member findMember = memberRepository.findById(save.getMemberId())
                .orElseThrow(MemberNotFound::new);
        assertEquals("12345", findMember.getPassword());

    }

    @DisplayName("회원이 삭제가 되어야함")
    @Test
    void delete_o() {
        // given
        Member createMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();

        Member save = memberRepository.save(createMember);

        // when
        memberService.delete(save.getMemberId());

        // then
        assertEquals(0L, memberRepository.count());

    }

}