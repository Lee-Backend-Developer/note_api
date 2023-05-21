package com.note.api.repository;

import com.note.api.entity.Note;

import java.util.List;

public interface NoteCustom {

    List<Note> findNoteByMemberId(Long memberId);

}
