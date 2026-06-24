--liquibase formatted sql

--changeset study-materials:006
-- password: admin123
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$8q0x7E6IJWvhEf7zWPIPD.UcwPFmDA7iQLNeUeBzF1Fw5RR7QJRpq', 'ADMIN');
-- password: student123
INSERT INTO users (username, password, role)
VALUES ('student', '$2a$10$nLEFq4Ui3Y80UtZ./NexXOiiu.T5ySjkKy3nYQrSKNJr3N1xL2ex2', 'USER');

--changeset study-materials:007
INSERT INTO categories (name, description)
VALUES ('Лекции', 'Текстовые и презентационные материалы');
INSERT INTO categories (name, description)
VALUES ('Лабораторные', 'Методические указания к лабораторным работам');
INSERT INTO categories (name, description)
VALUES ('Справочники', 'Краткие справочные материалы');

--changeset study-materials:008
INSERT INTO educational_materials (title, description, author, category_id, created_at)
VALUES ('Основы Java', 'Введение в ООП и синтаксис Java', 'Иванов И.И.', 1, '2024-09-01 10:00:00');
INSERT INTO educational_materials (title, description, author, category_id, created_at)
VALUES ('Spring Boot REST', 'Построение REST API на Spring', 'Петров П.П.', 1, '2024-10-15 12:30:00');
INSERT INTO educational_materials (title, description, author, category_id, created_at)
VALUES ('SQL и проектирование БД', 'Нормализация, индексы, транзакции', 'Сидорова М.А.', 2, '2024-11-01 09:00:00');
INSERT INTO educational_materials (title, description, author, category_id, created_at)
VALUES ('Коллекции Java', 'List, Set, Map и Stream API', 'Козлов Д.В.', 2, '2024-11-20 14:00:00');
INSERT INTO educational_materials (title, description, author, category_id, created_at)
VALUES ('Шпаргалка Git', 'Основные команды Git', 'Орлова Е.С.', 3, '2025-01-10 08:00:00');

--changeset study-materials:009
INSERT INTO methodicals (title, description, subject, edition_year, material_id)
VALUES ('Методичка: Лабораторная №1', 'Работа с коллекциями в Java', 'Программирование', 2024, 1);
INSERT INTO methodicals (title, description, subject, edition_year, material_id)
VALUES ('Методичка: Лабораторная №2', 'JPA и Spring Data', 'Базы данных', 2025, 3);
INSERT INTO methodicals (title, description, subject, edition_year, material_id)
VALUES ('Методичка: REST-сервис', 'Разработка REST API', 'Программирование', 2025, 2);
INSERT INTO methodicals (title, description, subject, edition_year, material_id)
VALUES ('Методичка: Stream API', 'Потоковая обработка данных', 'Программирование', 2024, 4);

--changeset study-materials:010
INSERT INTO stored_files (original_name, stored_name, content_type, size, uploaded_at, material_id)
VALUES ('java-basics.txt', 'seed_java-basics.txt', 'text/plain', 87, '2024-09-01 10:05:00', 1);
INSERT INTO stored_files (original_name, stored_name, content_type, size, uploaded_at, material_id)
VALUES ('spring-rest.txt', 'seed_spring-rest.txt', 'text/plain', 72, '2024-10-15 12:35:00', 2);
