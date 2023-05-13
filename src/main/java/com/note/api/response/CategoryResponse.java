package com.note.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CategoryResponse {
    private Long categoryId;
    private String name;
}
