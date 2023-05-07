package com.note.api.controller;

import com.note.api.request.member.MemberLogin;
import com.note.api.request.member.MemberPwdChange;
import com.note.api.request.member.MemberRegister;
import com.note.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("login")
    public void singIn(@RequestBody MemberLogin request) {
        memberService.singIn(request);
    }

    @PostMapping("register")
    public void singUp(@RequestBody MemberRegister request) {
        memberService.singUp(request);
    }

    @PutMapping("password")
    public void passwordChange(@RequestBody MemberPwdChange request, @SessionAttribute(name = "memberId") Long memberId){
        memberService.changePwd(memberId, request);
    }

    @DeleteMapping("delete")
    public void delete(@SessionAttribute(name = "memberId") Long memberId){
        memberService.delete(memberId);
    }

}
