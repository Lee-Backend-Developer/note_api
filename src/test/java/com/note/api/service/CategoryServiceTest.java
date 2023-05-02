package com.note.api.service;

import com.note.api.entity.Member;
import com.note.api.exception.CategoryLimit;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.request.CategoryCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

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

    @DisplayName("카테고리가 만들어져야됨")
    @Test
    void create_o() {
        // given
        CategoryCreate request = CategoryCreate.builder()
                .name("카테고리1")
                .build();

        // when
        Member findMember = getMember();
        categoryService.create(findMember.getMemberId(), request);

        // then
        assertEquals(1L, categoryRepository.count());
    }

    @DisplayName("회원이 카테고리 5개 이상일 경우 에러가 떠야한다")
    @Test
    void create_x() {
        // given

        // when
        Member findMember = getMember();

        assertThrows(CategoryLimit.class, () -> {
            for (int i = 0; i < 6; i++) {
                CategoryCreate request = CategoryCreate.builder()
                        .name("카테고리" + i)
                        .build();
                categoryService.create(findMember.getMemberId(), request);
            }

        });
    }


    private Member getMember() {
        return memberRepository.findByLoginIdAndPassword("test", "1234")
                .orElseThrow(MemberNotFound::new);
    }
}