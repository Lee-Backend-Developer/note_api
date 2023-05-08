package com.note.api.service;


import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.exception.CategoryLimit;
import com.note.api.exception.CategoryNotFount;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.request.category.CategoryCreate;
import com.note.api.request.category.CategoryNameChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Category create(Long memberId, CategoryCreate request) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        int size = memberRepository.countAddedCategories();

        if(size >= 5) {
            throw new CategoryLimit();
        }

        Category saveCategory = Category.builder()
                .member(findMember)
                .name(request.getName())
                .build();

        return categoryRepository.save(saveCategory);
    }


    public List<Category> getCategory() {
        return categoryRepository.findCategoryByMemberId();
    }


    @Transactional
    public void edit(Long categoryId, CategoryNameChange categoryNameChange) {
        Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFount::new);

        findCategory.edit(categoryNameChange.getName());
    }

    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFount::new);

        categoryRepository.delete(category);
    }
}
