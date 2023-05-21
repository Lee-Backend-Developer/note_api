package com.note.api.service;

import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.entity.Note;
import com.note.api.exception.CategoryNotFount;
import com.note.api.exception.MemberNotFound;
import com.note.api.exception.NoteNotEqual;
import com.note.api.exception.NoteNotFount;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.repository.NoteRepository;
import com.note.api.request.note.NoteCreate;
import com.note.api.request.note.NoteDelete;
import com.note.api.request.note.NoteEdit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NoteService {
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    // todo 회원, 메모 생성된 날짜 jpa 적용

    @Transactional
    public Note createNote(Long memberId, NoteCreate request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFount::new);

        Note createNote = Note.builder()
                .member(findMember)
                .content(request.getContent())
                .category(findCategory)
                .build();

        return noteRepository.save(createNote);
    }

    @Transactional
    public void editNote(Long noteId, NoteEdit request) {
        Note findNote = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFount::new);

        validationNote(noteId, request.getEditorMemberId());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFount::new);

        findNote.edit(request.getContent(), category);
    }

    @Transactional
    public void deleteNote(Long noteId, NoteDelete request) {
        Note findNote = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFount::new);

        validationNote(noteId, request.getMemberId());

        noteRepository.delete(findNote);
    }

    public List<Note> getNote(Long memberId) {
        return noteRepository.findNoteByMemberId();
    }

    private void validationNote(Long noteId, Long memberId) {
        Note findNote = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFount::new);

        Member noteMember = findNote.getMember();
        if(noteMember.getMemberId() != memberId){
            throw new NoteNotEqual();
        }
    }
}
