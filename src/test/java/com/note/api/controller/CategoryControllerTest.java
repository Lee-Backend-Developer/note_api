package com.note.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.repository.NoteRepository;
import com.note.api.request.category.CategoryCreate;
import com.note.api.request.category.CategoryNameChange;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void init_create() {
        Member createMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();

        memberRepository.save(createMember);
    }

    @AfterEach
    void init_delete() {
        noteRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("회원 카테고리가 만들어져야함")
    @Test
    void add() throws Exception {
        // given
        CategoryCreate categoryCreate = CategoryCreate.builder()
                .name("testCategory")
                .build();

        // expected
        MockHttpSession session = getSession();
        mockMvc.perform(post("/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryCreate))
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("카테고리 조회")
    @Test
    void find() throws Exception {
        // given

        // expected
        mockMvc.perform(get("/category"))
                .andDo(print());
    }

    @DisplayName("카테고리 이름이 변경이 되어야함")
    @Test
    void name_change() throws Exception {
        // given
        Member member = getMember();
        Category category = Category.builder()
                .member(member)
                .name("변경 전")
                .build();
        categoryRepository.save(category);

        CategoryNameChange request = CategoryNameChange.builder()
                .name("변경 후")
                .build();

        //expected
        Long categoryId = category.getCategoryId();
        mockMvc.perform(put("/category/{categoryId}/change", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 삭제가 되어야한다")
    @Test
    void category_delete() throws Exception {
        // given
        Member member = getMember();
        Category category = Category.builder()
                .member(member)
                .name("삭제")
                .build();
        categoryRepository.save(category);

        // expected
        Long categoryId = category.getCategoryId();
        mockMvc.perform(delete("/category/{categoryId}/delete", categoryId))
                .andExpect(status().isOk());

        Assertions.assertEquals(0L, categoryRepository.count());
    }

    private Member getMember() {
        return memberRepository.findAll().get(0);
    }

    private MockHttpSession getSession() {
        Member member = getMember();
        MockHttpSession session = new MockHttpSession();

        session.setAttribute("memberId", member.getMemberId());

        return session;
    }


}