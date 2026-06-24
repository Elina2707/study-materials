package com.study.materials.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String originalName;
    private String contentType;
    private Long size;
    private Instant uploadedAt;
}
