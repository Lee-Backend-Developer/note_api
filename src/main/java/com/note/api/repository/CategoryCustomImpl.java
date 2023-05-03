package com.note.api.repository;

import com.note.api.entity.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

import static com.note.api.entity.QCategory.category;
import static com.note.api.entity.QMember.member;

@Slf4j
@RequiredArgsConstructor
public class CategoryCustomImpl implements CategoryCustom {

    private final JPAQueryFactory queryFactory;

//    @Override
    public List<Category> findCategoryByMemberId() {
        return queryFactory.selectFrom(category)
                .join(category.member, member)
                .where(category.member.memberId.eq(member.memberId))
                .fetch();
    }
}
