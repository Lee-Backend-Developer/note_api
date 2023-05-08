package com.note.api.controller;

import com.note.api.request.category.CategoryCreate;
import com.note.api.request.category.CategoryNameChange;
import com.note.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity add(@RequestBody CategoryCreate request, @SessionAttribute("memberId") Long memberId) {

        categoryService.create(memberId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{categoryId}/change")
    public ResponseEntity nameChange(@PathVariable(name = "categoryId") Long categoryId, @RequestBody CategoryNameChange request) {

        categoryService.edit(categoryId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{categoryId}/delete")
    public ResponseEntity delete(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity(HttpStatus.OK);

    }
}
