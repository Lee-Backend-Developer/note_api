package com.note.api.repository;

import com.note.api.entity.Category;
import com.note.api.entity.QCategory;
import com.note.api.entity.QMember;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.note.api.entity.QCategory.category;
import static com.note.api.entity.QMember.member;


@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberCustomImpl implements MemberCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public int countAddedCategories() {

        List<Category> resultQuery = queryFactory.selectFrom(category)
                .join(category.member, member)
                .where(category.member.eq(member))
                .fetch();

        return resultQuery.size();
    }
}
