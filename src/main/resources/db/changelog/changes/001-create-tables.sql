--liquibase formatted sql

--changeset study-materials:001
CREATE TABLE IF NOT EXISTS users (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

--changeset study-materials:002
CREATE TABLE IF NOT EXISTS categories (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(128) NOT NULL UNIQUE,
    description VARCHAR(512)
);

--changeset study-materials:003
CREATE TABLE IF NOT EXISTS educational_materials (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(256) NOT NULL,
    description VARCHAR(2000),
    author      VARCHAR(128),
    category_id BIGINT,
    created_at  DATETIME(6)  NOT NULL,
    CONSTRAINT fk_material_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

--changeset study-materials:004
CREATE TABLE IF NOT EXISTS methodicals (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(256) NOT NULL,
    description  VARCHAR(2000),
    subject      VARCHAR(128) NOT NULL,
    edition_year INT,
    material_id  BIGINT,
    CONSTRAINT fk_methodical_material FOREIGN KEY (material_id) REFERENCES educational_materials (id) ON DELETE SET NULL
);

--changeset study-materials:005
CREATE TABLE IF NOT EXISTS stored_files (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    stored_name   VARCHAR(255) NOT NULL UNIQUE,
    content_type  VARCHAR(128),
    size          BIGINT       NOT NULL,
    uploaded_at   DATETIME(6)  NOT NULL,
    material_id   BIGINT,
    CONSTRAINT fk_file_material FOREIGN KEY (material_id) REFERENCES educational_materials (id) ON DELETE SET NULL
);
