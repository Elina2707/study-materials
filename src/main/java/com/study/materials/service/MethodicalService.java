package com.study.materials.service;

import com.study.materials.dto.MethodicalResponse;
import com.study.materials.repository.MethodicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MethodicalService {

    private final MethodicalRepository methodicalRepository;

    public List<MethodicalResponse> findAll() {
        return methodicalRepository.findAll().stream().map(MethodicalResponse::from).toList();
    }

    public MethodicalResponse findById(Long id) {
        return methodicalRepository.findById(id)
                .map(MethodicalResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Methodical not found: " + id));
    }

    public List<MethodicalResponse> findBySubject(String subject) {
        return methodicalRepository.findBySubjectContainingIgnoreCase(subject).stream()
                .map(MethodicalResponse::from).toList();
    }

    public List<MethodicalResponse> findByMaterialId(Long materialId) {
        return methodicalRepository.findByLinkedMaterialId(materialId).stream()
                .map(MethodicalResponse::from).toList();
    }
}
