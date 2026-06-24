package com.study.materials.controller;

import com.study.materials.dto.MethodicalResponse;
import com.study.materials.service.MethodicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/methodicals")
@RequiredArgsConstructor
public class MethodicalController {

    private final MethodicalService methodicalService;

    @GetMapping
    public List<MethodicalResponse> getAll() {
        return methodicalService.findAll();
    }

    @GetMapping("/{id}")
    public MethodicalResponse getById(@PathVariable Long id) {
        return methodicalService.findById(id);
    }

    @GetMapping("/by-subject")
    public List<MethodicalResponse> getBySubject(@RequestParam String subject) {
        return methodicalService.findBySubject(subject);
    }

    @GetMapping("/by-material")
    public List<MethodicalResponse> getByMaterial(@RequestParam Long materialId) {
        return methodicalService.findByMaterialId(materialId);
    }
}
