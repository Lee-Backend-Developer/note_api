package com.note.api.repository;

import com.note.api.entity.Category;

import java.util.List;

public interface CategoryCustom {

    List<Category> findCategoryByMemberId(Long memberId);

}
