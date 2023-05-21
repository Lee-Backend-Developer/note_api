package com.note.api.service;

import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.entity.Note;
import com.note.api.exception.MemberNotFound;
import com.note.api.exception.NoteNotEqual;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.repository.NoteRepository;
import com.note.api.request.note.NoteCreate;
import com.note.api.request.note.NoteDelete;
import com.note.api.request.note.NoteEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class NoteServiceTest {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NoteService noteService;

    @BeforeEach
    void create() {
        Member createMember = Member.builder()
                .loginId("test")
                .password("1234")
                .build();
        memberRepository.save(createMember);

        Category category = Category.builder()
                .member(createMember)
                .name("테스트1")
                .build();

        categoryRepository.save(category);
    }

    @AfterEach
    void clean() {
        noteRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("메모가 저장이 되어야함")
    @Test
    void create_note_o() {
        // given
        Member createMember = getMember();
        Category category = getCategory();

        NoteCreate request = NoteCreate.builder()
                .content("내용 테스트 입니다.")
                .categoryId(category.getCategoryId())
                .build();

        // when
        Note saveNote = noteService.createNote(createMember.getMemberId(), request);

        // then
        assertEquals("내용 테스트 입니다.", saveNote.getContent());
        assertEquals(1L, noteRepository.count());
    }

    @DisplayName("본인이 작성한 메모 조회")
    @Test
    void note_find_O() {
        // given
        Member member = getMember();

        Category category = Category.builder()
                .member(member)
                .name("카테고리1")
                .build();
        categoryRepository.save(category);

        Note note = Note.builder()
                .member(member)
                .content("test1")
                .category(category)
                .build();
        noteRepository.save(note);

        // when
        List<Note> notes = noteService.getNote(member.getMemberId());

        // then
        assertEquals("test1", notes.get(0).getContent());
        assertEquals("카테고리1", notes.get(0).getCategory().getName());
        assertEquals(1L, notes.size());

    }

    @DisplayName("메모가 수정이 되어야한다")
    @Test
    void edit_note_o() {
        // given
        Member createMember = getMember();

        Category createCategory = Category.builder()
                .member(createMember)
                .name("작업1")
                .build();
        categoryRepository.save(createCategory);

        NoteCreate noteCreateRequest = NoteCreate.builder()
                .content("내용 테스트 입니다.")
                .categoryId(createCategory.getCategoryId())
                .build();
        Note note = noteService.createNote(createMember.getMemberId(), noteCreateRequest);

        Category category = categoryRepository.findAll().get(0);
        String editContent = "수정된 노트입니다";

        NoteEdit noteEditRequest = NoteEdit.builder()
                .editorMemberId(createMember.getMemberId())
                .content("수정된 노트입니다")
                .categoryId(category.getCategoryId())
                .build();

        // when
        noteService.editNote(note.getNoteId(), noteEditRequest);

        // then
        assertEquals(editContent, note.getContent());
    }

    @DisplayName("노트 작성가 아닌사람이 수정할 경우 에러가 나와야한다")
    @Test
    void edit_note_x() {
        // given
        Member modifiedWriter = Member.builder()
                .loginId("test1")
                .password("1234")
                .build();
        memberRepository.save(modifiedWriter);

        Member writer = getMember();
        Category category = getCategory();
        Note note = Note.builder()
                .member(writer)
                .category(category)
                .content("수정이 안되어야함")
                .build();
        noteRepository.save(note);

        NoteEdit noteEdit = NoteEdit.builder()
                .editorMemberId(3L)
                .content("수정이 되었습니다")
                .categoryId(category.getCategoryId())
                .build();

        // expected
        assertThrows(NoteNotEqual.class, () -> {
            noteService.editNote(note.getNoteId(), noteEdit);
        });

    }

    @DisplayName("노트가 삭제가 되어야한다")
    @Test
    void delete_note_o() {
        // given
        Member member = getMember();
        Note createNote = Note.builder()
                .content("삭제될 메모")
                .member(member)
                .build();

        Note saveNote = noteRepository.save(createNote);

        NoteDelete noteDelete = NoteDelete.builder()
                .memberId(member.getMemberId())
                .build();

        // when
        noteService.deleteNote(saveNote.getNoteId(), noteDelete);

        // then
        assertEquals(0L, noteRepository.count());

    }

    private Member getMember() {
        return memberRepository.findByLoginIdAndPassword("test", "1234")
                .orElseThrow(MemberNotFound::new);
    }

    private Category getCategory(){
        return categoryRepository.findAll().get(0);
    }

}