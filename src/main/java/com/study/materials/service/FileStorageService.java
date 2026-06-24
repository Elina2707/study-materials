package com.study.materials.service;

import com.study.materials.dto.FileUploadResponse;
import com.study.materials.entity.EducationalMaterial;
import com.study.materials.entity.StoredFile;
import com.study.materials.repository.EducationalMaterialRepository;
import com.study.materials.repository.StoredFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final StoredFileRepository storedFileRepository;
    private final EducationalMaterialRepository materialRepository;

    @Value("${app.upload-dir}")
    private String uploadDir;

    public FileUploadResponse upload(MultipartFile file, Long materialId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File exceeds 10MB limit");
        }

        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        String storedName = UUID.randomUUID() + "_" + sanitize(file.getOriginalFilename());
        Path target = uploadPath.resolve(storedName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        EducationalMaterial material = null;
        if (materialId != null) {
            material = materialRepository.findById(materialId)
                    .orElseThrow(() -> new IllegalArgumentException("Material not found: " + materialId));
        }

        StoredFile entity = StoredFile.builder()
                .originalName(file.getOriginalFilename())
                .storedName(storedName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .uploadedAt(Instant.now())
                .material(material)
                .build();

        entity = storedFileRepository.save(entity);
        return new FileUploadResponse(
                entity.getId(),
                entity.getOriginalName(),
                entity.getContentType(),
                entity.getSize(),
                entity.getUploadedAt()
        );
    }

    public Resource loadAsResource(Long id) throws IOException {
        StoredFile meta = storedFileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));
        Path path = Paths.get(uploadDir).resolve(meta.getStoredName());
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found on disk");
        }
        return resource;
    }

    public StoredFile getMetadata(Long id) {
        return storedFileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));
    }

    public void delete(Long id) throws IOException {
        StoredFile meta = storedFileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));
        Path path = Paths.get(uploadDir).resolve(meta.getStoredName());
        Files.deleteIfExists(path);
        storedFileRepository.delete(meta);
    }

    private String sanitize(String name) {
        if (name == null) {
            return "file";
        }
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
