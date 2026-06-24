package com.study.materials.dto;

import com.study.materials.entity.Methodical;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MethodicalResponse {
    private Long id;
    private String title;
    private String description;
    private String subject;
    private Integer editionYear;
    private Long linkedMaterialId;
    private String linkedMaterialTitle;

    public static MethodicalResponse from(Methodical m) {
        return MethodicalResponse.builder()
                .id(m.getId())
                .title(m.getTitle())
                .description(m.getDescription())
                .subject(m.getSubject())
                .editionYear(m.getEditionYear())
                .linkedMaterialId(m.getLinkedMaterial() != null ? m.getLinkedMaterial().getId() : null)
                .linkedMaterialTitle(m.getLinkedMaterial() != null ? m.getLinkedMaterial().getTitle() : null)
                .build();
    }
}
