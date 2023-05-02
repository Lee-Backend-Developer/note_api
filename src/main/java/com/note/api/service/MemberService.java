package com.note.api.service;

import com.note.api.entity.Member;
import com.note.api.exception.MemberDuplication;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.MemberRepository;
import com.note.api.request.MemberLogin;
import com.note.api.request.MemberPwdChange;
import com.note.api.request.MemberRegister;
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

    @Transactional
    public Member singUp(MemberRegister request){
        Optional<Member> findMember = memberRepository.findByLoginId(request.getLoginId());
        if(findMember.isPresent()){
            throw new MemberDuplication();
        }

        Member createMember = Member.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .build();

        Member save = memberRepository.save(createMember);

        return save;
    }

    public Member singIn(MemberLogin request) {
        Member findMember = memberRepository.findByLoginIdAndPassword(request.getLoginId(), request.getPassword())
                .orElseThrow(MemberNotFound::new);

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
