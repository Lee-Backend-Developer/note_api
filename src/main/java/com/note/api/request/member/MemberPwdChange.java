package com.note.api.request.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPwdChange {
    private String currentPwd;
    private String changePwd;
}
