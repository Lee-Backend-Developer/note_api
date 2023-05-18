package com.note.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.note.api.entity.Member;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.MemberRepository;
import com.note.api.request.member.MemberLogin;
import com.note.api.request.member.MemberPwdChange;
import com.note.api.request.member.MemberRegister;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.handler.MockHandlerFactory;
import org.mockito.invocation.MockHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원이 로그인이 되어야한다")
    @Test
    void singIn() throws Exception {
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


        // expected
        Member findMember = memberRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword())
                .orElseThrow(MemberNotFound::new);

        mockMvc.perform(post("/member/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("회원가입이 안된 회원은 로그인이 되지않아야한다")
    @Test
    void singIn_x() throws Exception {
        // given

        MemberLogin request = MemberLogin.builder()
                .loginId("test")
                .password("1234")
                .build();


        // expected
        mockMvc.perform(post("/member/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("회원가입이 되어야한다")
    @Test
    void register_O() throws Exception {
        // given

        MemberRegister request = MemberRegister.builder()
                .loginId("test")
                .password("1234")
                .build();

        // expected
        mockMvc.perform(post("/member/register")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("중복 회원가입은 되지 않아야한다.")
    @Test
    void register_X() throws Exception {
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
        mockMvc.perform(post("/member/register")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("비밀번호가 변경이 되어야한다")
    @Test
    void change_pwd_O() throws Exception {
        // given

        Member saveMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();
        memberRepository.save(saveMember);

        MemberPwdChange request = MemberPwdChange.builder()
                .currentPwd(saveMember.getPassword())
                .changePwd("0000")
                .build();


        // expected
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", saveMember.getMemberId());

        mockMvc.perform(put("/member/password")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원이 삭제가 되어야함")
    @Test
    void delete_O() throws Exception {
        // given
        Member saveMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();
        memberRepository.save(saveMember);

        // expected
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", saveMember.getMemberId());

        mockMvc.perform(delete("/member/delete").session(session))
                .andExpect(status().isOk())
                .andDo(print());


    }


}