package com.note.api.controller;

import com.note.api.entity.Note;
import com.note.api.request.note.NoteCreate;
import com.note.api.request.note.NoteEdit;
import com.note.api.response.NoteResponse;
import com.note.api.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    /**
     *    todo
     *    메소드 마다 SessionAttribute를 추가해야하는데 어떻게 하면 안쓰고도 세션값을 받을지 생각해보기
     */

    @PostMapping("write")
    public ResponseEntity add(@RequestBody NoteCreate request, @SessionAttribute(name = "memberId") Long memberId){
        noteService.createNote(memberId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<NoteResponse> notes(@SessionAttribute(name = "memberId") Long memberId) {
        List<NoteResponse> response = noteService.getNote(memberId)
                .stream().map(note -> NoteResponse.builder()
                        .noteId(note.getNoteId())
                        .content(note.getContent())
                        .category(note.getCategory().getName())
                        .build()).collect(Collectors.toList());

        return response;
    }

    @PutMapping("{noteId}/edit")
    public ResponseEntity edit(@RequestBody NoteEdit request, @PathVariable("noteId") Long noteId) {
        noteService.editNote(noteId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{noteId}/delete")
    public ResponseEntity delete(@PathVariable("noteId") Long noteId) {
        noteService.deleteNote(noteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
