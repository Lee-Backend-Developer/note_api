package com.note.api.request;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class MemberRegister {
    private String loginId;
    private String password;
}
