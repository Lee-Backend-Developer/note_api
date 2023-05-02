package com.note.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryCreate {
    private String name;
}
