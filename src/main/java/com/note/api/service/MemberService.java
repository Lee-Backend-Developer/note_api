package com.note.api.service;

import com.note.api.crypto.PasswordEncoder;
import com.note.api.entity.Member;
import com.note.api.exception.MemberDuplication;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.MemberRepository;
import com.note.api.request.member.MemberLogin;
import com.note.api.request.member.MemberPwdChange;
import com.note.api.request.member.MemberRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member singUp(MemberRegister request){
        Optional<Member> findMember = memberRepository.findByLoginId(request.getLoginId());

        if(findMember.isPresent()) {
            throw new MemberDuplication();
        }

        String encryptPwd = passwordEncoder.encrypt(request.getPassword());

        Member createMember = Member.builder()
                .loginId(request.getLoginId())
                .password(encryptPwd)
                .build();

        Member save = memberRepository.save(createMember);

        return save;
    }

    public Member singIn(MemberLogin request) {

        Member findMember = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(MemberNotFound::new);

        if (!passwordEncoder.matches(request.getPassword(), findMember.getPassword())) {
            throw new MemberNotFound();
        }

        return findMember;
    }

    @Transactional
    public void changePwd(Long memberId, MemberPwdChange request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        if(!findMember.getPassword().equals(request.getCurrentPwd())){
            throw new RuntimeException("비밀번호가 틀립니다.");
        }

        findMember.editPwd(request.getChangePwd());
    }

    @Transactional
    public void delete(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        memberRepository.delete(findMember);
    }


}
