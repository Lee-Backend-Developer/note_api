package com.note.api.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1860272738L;

    public static final QMember member = new QMember("member1");

    public final ListPath<Category, QCategory> categories = this.<Category, QCategory>createList("categories", Category.class, QCategory.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final StringPath loginId = createString("loginId");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<Note, QNote> noteList = this.<Note, QNote>createList("noteList", Note.class, QNote.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

