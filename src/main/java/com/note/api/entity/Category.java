package com.note.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member = new Member();
    private String name;

    private Date created;
    private Date edited;

    @Builder
    public Category(Member member, String name) {
        this.member = member;
        this.name = name;
    }

    //비즈니스 로직

    public void edit(String name){
        this.name = name;
    }
}
