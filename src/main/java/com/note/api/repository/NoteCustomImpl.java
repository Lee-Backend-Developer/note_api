package com.note.api.repository;

import com.note.api.entity.Note;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.note.api.entity.QMember.*;
import static com.note.api.entity.QNote.*;

@RequiredArgsConstructor
@Repository
public class NoteCustomImpl implements NoteCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Note> findNoteByMemberId() {
        return queryFactory.selectFrom(note)
                .join(note.member, member)
                .where(note.member.memberId.eq(member.memberId))
                .fetch();
    }
}
