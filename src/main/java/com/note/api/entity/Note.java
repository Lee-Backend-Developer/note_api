package com.note.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note {

    @Id
    @GeneratedValue
    private Long noteId;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Category category;

    private String content;

    private Date created;
    private Date edited;

    @Builder
    public Note(Member member, Category category, String content) {
        this.member = member;
        this.category = category;
        this.content = content;
    }

    //비즈니스 로직

    public void edit(String content, Category category) {
        this.content = content;
        this.category = category;
    }
}
