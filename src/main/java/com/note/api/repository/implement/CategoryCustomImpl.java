package com.note.api.repository.implement;

import com.note.api.entity.Category;
import com.note.api.repository.CategoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.note.api.entity.QCategory.*;
import static com.note.api.entity.QMember.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CategoryCustomImpl implements CategoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Category> findCategoryByMemberId() {
        return queryFactory.selectFrom(category)
                .join(category.member, member)
                .where(category.member.memberId.eq(member.memberId))
                .fetch();
    }
}
