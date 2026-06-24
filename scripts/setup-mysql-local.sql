-- Локальный MySQL: пользователь study (вместо root на Linux — ошибка 1698).
-- Выполнить: sudo mysql < scripts/setup-mysql-local.sql

CREATE DATABASE IF NOT EXISTS study_materials
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'study'@'localhost' IDENTIFIED BY 'StudyRoot2026!';
CREATE USER IF NOT EXISTS 'study'@'%' IDENTIFIED BY 'StudyRoot2026!';

GRANT ALL PRIVILEGES ON study_materials.* TO 'study'@'localhost';
GRANT ALL PRIVILEGES ON study_materials.* TO 'study'@'%';

FLUSH PRIVILEGES;
