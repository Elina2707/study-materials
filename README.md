# Study Materials API

REST API на Spring Boot для **учёта учебных материалов и методичек**.

Инструкции ниже — для **Windows 10/11** (CMD и PowerShell).

## Требования

| Компонент | Зачем |
|-----------|--------|
| [JDK 17](https://adoptium.net/) | Запуск Spring Boot |
| [Maven](https://maven.apache.org/download.cgi) | Сборка (`mvn`) |
| [Docker Desktop](https://www.docker.com/products/docker-desktop/) | MySQL в контейнере (рекомендуется) |
| Git / curl | По желанию (curl есть в Windows 10+) |

Проверка в **PowerShell**:

```powershell
java -version
mvn -version
docker compose version
```

## Стек

- Java 17, Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA + MySQL 8 + Liquibase
- Swagger UI (springdoc-openapi)
- Загрузка файлов (до 10 MB)
- Docker Desktop + Compose

## Сущности (5)

| Entity | Описание |
|--------|----------|
| `User` | Пользователи и роли (USER, ADMIN) |
| `Category` | Категории материалов |
| `EducationalMaterial` | Учебные материалы |
| `Methodical` | Методички |
| `StoredFile` | Метаданные загруженных файлов |

## Авторизация

JWT в заголовке: `Authorization: Bearer <token>`

Тестовые пользователи (Liquibase при первом запуске):

| Логин | Пароль | Роль |
|-------|--------|------|
| admin | admin123 | ADMIN |
| student | student123 | USER |

### Схема и данные (Liquibase)

```
src\main\resources\db\
├── changelog\
│   ├── db.changelog-master.yaml
│   └── changes\
│       ├── 001-create-tables.sql
│       └── 002-insert-data.sql
└── seed-files\
    ├── java-basics.txt
    └── spring-rest.txt
```

При старте Liquibase создаёт таблицы и вставляет фиксированные записи. Файлы из `seed-files\` копируются в папку загрузок (`SeedFileLoader`).

Пересоздать БД с нуля:

```powershell
docker compose down -v
docker compose up --build
```

### Важно: JWT и bcrypt

1. **JWT** — секрет в `application.yml` как обычная UTF-8 строка, без двойного Base64.
2. **bcrypt** — энкодер **`$2a$`**, совместим с хэшами Python (`$2b$`).

## Swagger

- UI: http://localhost:8080/swagger-ui.html
- OpenAPI: http://localhost:8080/v3/api-docs

1. `POST /api/auth/login` — `admin` / `admin123`
2. Скопируйте `token`
3. **Authorize** → вставьте JWT (часто без префикса `Bearer`)

## Запуск в Docker (рекомендуется)

1. Запустите **Docker Desktop** и дождитесь статуса *Running*.
2. В PowerShell:

```powershell
cd $env:USERPROFILE\Desktop\study-materials-api
docker compose up --build
```

- API: http://localhost:8080
- MySQL: `localhost:3306`, пользователь `study`, пароль `StudyRoot2026!`, БД `study_materials`

Остановка:

```powershell
docker compose down
```

С удалением данных:

```powershell
docker compose down -v
```

## Локальный запуск на Windows

По умолчанию приложение ждёт MySQL на `localhost:3306`, пользователь **`study`**, пароль `StudyRoot2026!`, БД `study_materials`.

### Вариант A: MySQL в Docker, приложение в IDE / Maven

```powershell
cd $env:USERPROFILE\Desktop\study-materials-api
docker compose up mysql -d
mvn spring-boot:run
```

В IntelliJ IDEA: Run `StudyMaterialsApplication` (профиль по умолчанию, без Docker для `api`).

### Вариант B: MySQL установлен на Windows

1. Установите [MySQL Community Server 8](https://dev.mysql.com/downloads/installer/) (MySQL Installer).
2. Запомните пароль **root**, запустите службу *MySQL80*.
3. Выполните скрипт (из папки проекта):

```powershell
cd $env:USERPROFILE\Desktop\study-materials-api
# Путь к mysql.exe — подставьте свой (типично):
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p < scripts\setup-mysql-local.sql
```

4. Запуск API:

```powershell
mvn spring-boot:run
```

### Переменные окружения (PowerShell)

```powershell
$env:DB_HOST = "localhost"
$env:DB_USER = "study"
$env:DB_PASSWORD = "StudyRoot2026!"
mvn spring-boot:run
```

В **CMD**:

```cmd
set DB_HOST=localhost
set DB_USER=study
set DB_PASSWORD=StudyRoot2026!
mvn spring-boot:run
```

| Переменная | По умолчанию |
|------------|--------------|
| `DB_HOST` | localhost |
| `DB_PORT` | 3306 |
| `DB_NAME` | study_materials |
| `DB_USER` | study |
| `DB_PASSWORD` | StudyRoot2026! |
| `UPLOAD_DIR` | uploads |
| `JWT_SECRET` | см. `application.yml` |

### Типичные проблемы на Windows

| Проблема | Решение |
|----------|---------|
| `docker compose` не найден | Установите Docker Desktop, перезапустите терминал |
| Порт 3306 занят | Остановите локальный MySQL в *services.msc* или смените порт в `docker-compose.yml` |
| `Access denied for user 'root'` | Не используйте root в приложении — выполните `scripts\setup-mysql-local.sql` для пользователя `study` |
| Liquibase / validate ошибка после смены схемы | `docker compose down -v`, затем снова `up --build` |

## Примеры запросов

### PowerShell

```powershell
$body = @{ username = "admin"; password = "admin123" } | ConvertTo-Json
$r = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method Post -ContentType "application/json" -Body $body
$token = $r.token

Invoke-RestMethod -Uri "http://localhost:8080/api/materials" -Headers @{ Authorization = "Bearer $token" }

# Загрузка файла
Invoke-RestMethod -Uri "http://localhost:8080/api/files/upload" -Method Post `
  -Headers @{ Authorization = "Bearer $token" } `
  -Form @{ file = Get-Item "C:\path\to\document.pdf"; materialId = 1 }
```

### curl (CMD / PowerShell)

```powershell
curl.exe -s -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

В PowerShell для многострочного `curl` удобнее использовать пример с `Invoke-RestMethod` выше.

## Эндпоинты

### Выборка (GET)

| URL | Описание |
|-----|----------|
| `/api/categories` | Все категории |
| `/api/categories/{id}` | Категория по id |
| `/api/materials` | Все материалы |
| `/api/materials/{id}` | Материал по id |
| `/api/materials/by-category?categoryId=` | По категории |
| `/api/materials/search?title=` | Поиск по названию |
| `/api/materials/by-author?author=` | По автору |
| `/api/methodicals` | Все методички |
| `/api/methodicals/{id}` | Методичка по id |
| `/api/methodicals/by-subject?subject=` | По предмету |
| `/api/methodicals/by-material?materialId=` | По материалу |

### Файлы

| Метод | URL |
|-------|-----|
| POST | `/api/files/upload` |
| GET | `/api/files/{id}` |
| DELETE | `/api/files/{id}` |

### Auth

| Метод | URL |
|-------|-----|
| POST | `/api/auth/register` |
| POST | `/api/auth/login` |
