package com.note.api.service;

import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.entity.Note;
import com.note.api.exception.MemberNotFound;
import com.note.api.exception.NoteNotFount;
import com.note.api.repository.MemberRepository;
import com.note.api.repository.NoteRepository;
import com.note.api.request.NoteCreate;
import com.note.api.request.NoteEdit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NoteService {
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;

    @Transactional
    public Note createNote(Long memberId, NoteCreate request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        Note createNote = Note.builder()
                .member(findMember)
                .content(request.getContent())
                .category(request.getCategory())
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
}
