package com.study.materials.dto;

import com.study.materials.entity.EducationalMaterial;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MaterialResponse {
    private Long id;
    private String title;
    private String description;
    private String author;
    private Long categoryId;
    private String categoryName;
    private Instant createdAt;

    public static MaterialResponse from(EducationalMaterial m) {
        return MaterialResponse.builder()
                .id(m.getId())
                .title(m.getTitle())
                .description(m.getDescription())
                .author(m.getAuthor())
                .categoryId(m.getCategory() != null ? m.getCategory().getId() : null)
                .categoryName(m.getCategory() != null ? m.getCategory().getName() : null)
                .createdAt(m.getCreatedAt())
                .build();
    }
}
