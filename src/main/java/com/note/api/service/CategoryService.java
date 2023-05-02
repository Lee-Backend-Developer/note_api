package com.note.api.service;


import com.note.api.entity.Category;
import com.note.api.entity.Member;
import com.note.api.exception.CategoryLimit;
import com.note.api.exception.MemberNotFound;
import com.note.api.repository.CategoryRepository;
import com.note.api.repository.MemberRepository;
import com.note.api.request.CategoryCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


        log.info("category size => {}", size);

        if(size >= 5) {
            throw new CategoryLimit();
        }

        Category saveCategory = Category.builder()
                .member(findMember)
                .name(request.getName())
                .build();

        log.info("saveCategory = {}", saveCategory.getName());

        return categoryRepository.save(saveCategory); // 정상
    }
}
