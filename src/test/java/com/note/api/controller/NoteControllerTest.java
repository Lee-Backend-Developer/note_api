package com.note.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.entity.Note;
import com.note.api.exception.NoteNotFount;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.repository.NoteRepository;
import com.note.api.request.note.NoteCreate;
import com.note.api.request.note.NoteEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class NoteControllerTest {

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

        Category createCategory = Category.builder()
                .name("t1")
                .member(createMember)
                .build();

        categoryRepository.save(createCategory);
    }

    @AfterEach
    void init_delete() {
        noteRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("메모가 추가 되어야한다.")
    @Test
    void create_o() throws Exception {
        // given
        Category category = getCategory();
        NoteCreate request = NoteCreate.builder()
                .content("메모를 추가하였음")
                .categoryId(category.getCategoryId())
                .build();

        // expected
        MockHttpSession session = getSession();

        mockMvc.perform(post("/note/write")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        assertEquals(1L, noteRepository.count());
    }

    @DisplayName("회원이 작성한 노트가 가져와야한다.")
    @Test
    void getNote() throws Exception {
        // given
        Member member = getMember();
        Category category = getCategory();

        for (int i = 0; i < 5; i++) {
            Note note = Note.builder()
                    .member(member)
                    .category(category)
                    .content("삭제 " + i)
                    .build();

            noteRepository.save(note);
        }

        // expected
        MockHttpSession session = getSession();
        mockMvc.perform(get("/note")
                        .session(session))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].category").value("t1"),
                        jsonPath("$[0].content").value("삭제 0"),
                        jsonPath("$[1].category").value("t1"),
                        jsonPath("$[1].content").value("삭제 1"))
                .andDo(print());
    }

    @DisplayName("노트가 수정되어야한다.")
    @Test
    void edit() throws Exception {
        // given
        Category category = getCategory();
        Member member = getMember();

        Note note = Note.builder()
                .content("수정이 되어야함")
                .category(category)
                .member(member)
                .build();

        noteRepository.save(note);

        NoteEdit request = NoteEdit.builder()
                .content("수정이 되었습니다.")
                .categoryId(category.getCategoryId())
                .build();

        //expected
        MockHttpSession session = getSession();
        mockMvc.perform(put("/note/{noteId}/edit", note.getNoteId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .session(session))
                .andExpect(status().isOk());

        Note findNote = noteRepository.findById(note.getNoteId()).orElseThrow(NoteNotFount::new);
        assertEquals("수정이 되었습니다.", findNote.getContent());
    }

    @DisplayName("노트가 삭제가 되어야한다")
    @Test
    void note_delete_O() throws Exception {
        // given
        Member member = getMember();
        Category category = getCategory();

        Note note = Note.builder()
                .member(member)
                .category(category)
                .build();
        noteRepository.save(note);

        // expected
        Long noteId = note.getNoteId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/note/{noteId}/delete", noteId));

        assertEquals(0L, noteRepository.count());
    }


    private Member getMember() {
        return memberRepository.findAll().get(0);
    }

    private Category getCategory() {
        return categoryRepository.findAll().get(0);
    }

    private MockHttpSession getSession() {
        Member member = getMember();
        MockHttpSession session = new MockHttpSession();

        session.setAttribute("memberId", member.getMemberId());

        return session;
    }

}