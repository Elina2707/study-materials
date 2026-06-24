package com.study.materials.repository;

import com.study.materials.entity.EducationalMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationalMaterialRepository extends JpaRepository<EducationalMaterial, Long> {
    List<EducationalMaterial> findByCategoryId(Long categoryId);
    List<EducationalMaterial> findByTitleContainingIgnoreCase(String title);
    List<EducationalMaterial> findByAuthorContainingIgnoreCase(String author);
}
