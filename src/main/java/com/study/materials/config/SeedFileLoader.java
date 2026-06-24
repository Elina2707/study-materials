package com.study.materials.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/**
 * Копирует небольшие файлы из classpath:db/seed-files/ в каталог загрузок.
 * Имена на диске соответствуют stored_name из 002-insert-data.sql.
 */
@Slf4j
@Component
public class SeedFileLoader implements ApplicationRunner {

    private static final Map<String, String> SEED_FILES = Map.of(
            "java-basics.txt", "seed_java-basics.txt",
            "spring-rest.txt", "seed_spring-rest.txt"
    );

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for (Map.Entry<String, String> entry : SEED_FILES.entrySet()) {
            String classpathName = entry.getKey();
            String storedName = entry.getValue();
            Path target = uploadPath.resolve(storedName);

            if (Files.exists(target)) {
                continue;
            }

            Resource resource = resolver.getResource("classpath:db/seed-files/" + classpathName);
            if (!resource.exists()) {
                log.warn("Seed file not found on classpath: {}", classpathName);
                continue;
            }

            try (InputStream in = resource.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("Copied seed file to {}", target);
        }
    }
}
