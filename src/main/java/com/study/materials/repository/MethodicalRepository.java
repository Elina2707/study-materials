package com.study.materials.repository;

import com.study.materials.entity.Methodical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MethodicalRepository extends JpaRepository<Methodical, Long> {
    List<Methodical> findBySubjectContainingIgnoreCase(String subject);
    List<Methodical> findByLinkedMaterialId(Long materialId);
}
