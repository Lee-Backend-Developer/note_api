package com.note.api.request.note;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteDelete {
    private Long memberId;

    public NoteDelete() {
    }

    public NoteDelete(Long memberId) {
        this.memberId = memberId;
    }
}
