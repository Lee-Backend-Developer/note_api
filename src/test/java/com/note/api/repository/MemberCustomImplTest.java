package com.note.api.repository;

import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.exception.MemberNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberCustomImplTest {

    @Autowired
    private MemberCustomImpl memberCustom;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void create(){
        Member createMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();
        memberRepository.save(createMember);
    }

    @Test
    void test() {
        Member findMember = getMember();

        Category categoryCreate = Category.builder()
                .name("c1")
                .member(findMember)
                .build();

        categoryRepository.save(categoryCreate);

        assertEquals(1, memberCustom.countAddedCategories());
    }

    private Member getMember() {
        return memberRepository.findByLoginIdAndPassword("test", "1234")
                .orElseThrow(MemberNotFound::new);
    }

}