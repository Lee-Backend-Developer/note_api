package com.note.api.service;

import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.entity.Note;
import com.note.api.exception.CategoryNotFount;
import com.note.api.exception.MemberNotFound;
import com.note.api.exception.NoteNotFount;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.repository.NoteRepository;
import com.note.api.request.note.NoteCreate;
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

    public void editNote(Long noteId, NoteEdit request) {
        Note findNote = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFount::new);

        findNote.edit(request.getContent(), request.getCategory());
    }

    @Transactional
    public void deleteNote(Long noteId) {
        Note findNote = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFount::new);

        noteRepository.delete(findNote);
    }

    public List<Note> getNote(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        return noteRepository.findNoteByMemberId();
    }
}
