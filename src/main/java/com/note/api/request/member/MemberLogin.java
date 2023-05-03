package com.note.api.request.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLogin {
    private String loginId;
    private String password;
}
