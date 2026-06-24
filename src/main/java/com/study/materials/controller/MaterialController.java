package com.study.materials.controller;

import com.study.materials.dto.MaterialResponse;
import com.study.materials.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public List<MaterialResponse> getAll() {
        return materialService.findAll();
    }

    @GetMapping("/{id}")
    public MaterialResponse getById(@PathVariable Long id) {
        return materialService.findById(id);
    }

    @GetMapping("/by-category")
    public List<MaterialResponse> getByCategory(@RequestParam Long categoryId) {
        return materialService.findByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<MaterialResponse> searchByTitle(@RequestParam String title) {
        return materialService.searchByTitle(title);
    }

    @GetMapping("/by-author")
    public List<MaterialResponse> getByAuthor(@RequestParam String author) {
        return materialService.searchByAuthor(author);
    }
}
