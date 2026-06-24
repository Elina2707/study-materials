package com.study.materials.service;

import com.study.materials.dto.MaterialResponse;
import com.study.materials.repository.EducationalMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final EducationalMaterialRepository materialRepository;

    public List<MaterialResponse> findAll() {
        return materialRepository.findAll().stream().map(MaterialResponse::from).toList();
    }

    public MaterialResponse findById(Long id) {
        return materialRepository.findById(id)
                .map(MaterialResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Material not found: " + id));
    }

    public List<MaterialResponse> findByCategory(Long categoryId) {
        return materialRepository.findByCategoryId(categoryId).stream()
                .map(MaterialResponse::from).toList();
    }

    public List<MaterialResponse> searchByTitle(String title) {
        return materialRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(MaterialResponse::from).toList();
    }

    public List<MaterialResponse> searchByAuthor(String author) {
        return materialRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(MaterialResponse::from).toList();
    }
}
