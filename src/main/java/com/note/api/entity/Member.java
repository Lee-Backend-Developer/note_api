package com.note.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;
    private String loginId;
    private String password;
    private Date created;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Note> noteList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }


    // 비즈니스 로직

    public void editPwd(String changePwd) {
        this.password = changePwd;
    }
}
