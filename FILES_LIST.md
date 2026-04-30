# 📂 Полный список файлов проекта IR Remote

## Обзор
Этот документ содержит полный список всех файлов проекта с кратким описанием их назначения.

---

## 📱 Основные файлы приложения

### Корневые файлы конфигурации

| Файл | Назначение |
|------|-----------|
| `build.gradle.kts` | Корневая конфигурация Gradle с версиями плагинов |
| `settings.gradle.kts` | Настройки проекта и подключение модулей |
| `gradle.properties` | Свойства Gradle (JVM, AndroidX, кэширование) |
| `gradlew.bat` | Gradle wrapper для Windows |
| `.gitignore` | Исключения для Git (build, IDE файлы) |
| `local.properties.template` | Шаблон для local.properties (SDK путь) |

### Gradle Wrapper

| Файл | Назначение |
|------|-----------|
| `gradle/wrapper/gradle-wrapper.properties` | Конфигурация Gradle 8.4 wrapper |

---

## 🏗️ Модуль приложения (app/)

### Конфигурация модуля

| Файл | Назначение |
|------|-----------|
| `app/build.gradle.kts` | Конфигурация модуля: зависимости, SDK, Compose |
| `app/proguard-rules.pro` | Правила ProGuard для release сборок |

### Манифест

| Файл | Назначение |
|------|-----------|
| `app/src/main/AndroidManifest.xml` | Разрешения, требования ИК-порта, активности |

---

## 💾 Слой данных (Data Layer)

### Сущности базы данных

| Файл | Назначение |
|------|-----------|
| `data/local/entity/DeviceEntity.kt` | Сущность устройства (id, name, type, brand, model) |
| `data/local/entity/IRCommandEntity.kt` | Сущность ИК-команды (deviceId, protocol, frequency, pattern) |

### DAO (Data Access Objects)

| Файл | Назначение |
|------|-----------|
| `data/local/dao/DeviceDao.kt` | CRUD операции для устройств с Flow |
| `data/local/dao/IRCommandDao.kt` | CRUD операции для ИК-команд с Flow |

### База данных

| Файл | Назначение |
|------|-----------|
| `data/local/IRRemoteDatabase.kt` | Конфигурация Room базы данных |

### Мапперы

| Файл | Назначение |
|------|-----------|
| `data/mapper/DeviceMapper.kt` | Преобразование DeviceEntity ↔ Device |
| `data/mapper/IRCommandMapper.kt` | Преобразование IRCommandEntity ↔ IRCommand с JSON |

### Репозитории (реализации)

| Файл | Назначение |
|------|-----------|
| `data/repository/DeviceRepositoryImpl.kt` | Реализация DeviceRepository |
| `data/repository/IRCommandRepositoryImpl.kt` | Реализация IRCommandRepository |

### Сервисы (реализации)

| Файл | Назначение |
|------|-----------|
| `data/service/IRTransmitterServiceImpl.kt` | Реализация работы с ConsumerIrManager |

---

## 🎯 Доменный слой (Domain Layer)

### Модели

| Файл | Назначение |
|------|-----------|
| `domain/model/Device.kt` | Доменная модель устройства + enum DeviceType |
| `domain/model/IRCommand.kt` | Доменная модель ИК-команды + enum IRProtocol |

### Репозитории (интерфейсы)

| Файл | Назначение |
|------|-----------|
| `domain/repository/DeviceRepository.kt` | Интерфейс операций с устройствами |
| `domain/repository/IRCommandRepository.kt` | Интерфейс операций с ИК-командами |

### Сервисы (интерфейсы)

| Файл | Назначение |
|------|-----------|
| `domain/service/IRTransmitterService.kt` | Интерфейс передачи ИК-сигналов + типы ошибок |

### Use Cases

| Файл | Назначение |
|------|-----------|
| `domain/usecase/DeviceUseCases.kt` | 8 use cases для работы с устройствами |
| `domain/usecase/IRCommandUseCases.kt` | 7 use cases для работы с ИК-командами |
| `domain/usecase/IRTransmitterUseCases.kt` | 3 use cases для работы с ИК-передатчиком |

---

## 🎨 Слой представления (Presentation Layer)

### Главная активность

| Файл | Назначение |
|------|-----------|
| `presentation/MainActivity.kt` | Точка входа приложения с навигацией |

### Тема приложения

| Файл | Назначение |
|------|-----------|
| `presentation/theme/Color.kt` | Цветовая палитра Material3 |
| `presentation/theme/Type.kt` | Типографика приложения |
| `presentation/theme/Theme.kt` | Конфигурация темы с динамическими цветами |

### Навигация

| Файл | Назначение |
|------|-----------|
| `presentation/navigation/Screen.kt` | Sealed class с маршрутами навигации |
| `presentation/navigation/AppNavigation.kt` | NavHost с конфигурацией всех экранов |

### Экраны

| Файл | Назначение |
|------|-----------|
| `presentation/screen/home/HomeViewModel.kt` | ViewModel главного экрана |
| `presentation/screen/home/HomeScreen.kt` | UI главного экрана (избранные + все устройства) |

### Компоненты

| Файл | Назначение |
|------|-----------|
| `presentation/components/DeviceCard.kt` | Карточка устройства с иконкой и избранным |

---

## 🔧 Dependency Injection

| Файл | Назначение |
|------|-----------|
| `di/DatabaseModule.kt` | Провайдеры Room базы данных и DAO |
| `di/RepositoryModule.kt` | Привязки репозиториев к реализациям |
| `di/ServiceModule.kt` | Привязки сервисов к реализациям |

---

## 📦 Класс приложения

| Файл | Назначение |
|------|-----------|
| `IRRemoteApplication.kt` | Application класс с @HiltAndroidApp |

---

## 🎨 Ресурсы (Resources)

### Строки и темы

| Файл | Назначение |
|------|-----------|
| `res/values/strings.xml` | Все строки приложения (русский/английский) |
| `res/values/themes.xml` | Конфигурация Material темы |

### XML конфигурации

| Файл | Назначение |
|------|-----------|
| `res/xml/data_extraction_rules.xml` | Правила резервного копирования (Android 12+) |
| `res/xml/backup_rules.xml` | Правила резервного копирования (legacy) |

---

## 🤖 CI/CD (GitHub Actions)

### Workflows

| Файл | Назначение |
|------|-----------|
| `.github/workflows/android-ci.yml` | Основная CI: сборка, тесты, lint |
| `.github/workflows/release.yml` | Автоматические релизы по тегам |
| `.github/workflows/code-quality.yml` | Проверка качества кода (tests, ktlint, detekt) |
| `.github/workflows/pr-checks.yml` | Проверки для Pull Requests + размер APK |
| `.github/workflows/security.yml` | Сканирование безопасности (CodeQL, dependencies) |

### Конфигурация

| Файл | Назначение |
|------|-----------|
| `.github/dependabot.yml` | Автоматическое обновление зависимостей |

---

## 📚 Документация

### Основная документация

| Файл | Назначение |
|------|-----------|
| `README.md` | Полное описание проекта (русский) |
| `PROJECT_STRUCTURE.md` | Детальная структура проекта (русский) |
| `SUMMARY.md` | Краткое резюме проекта и статус |
| `GETTING_STARTED.md` | Руководство по быстрому старту |
| `FILES_LIST.md` | Этот файл - список всех файлов |

### Документация CI/CD

| Файл | Назначение |
|------|-----------|
| `.github/WORKFLOWS.md` | Документация GitHub Actions (английский) |
| `.github/WORKFLOWS_RU.md` | Документация GitHub Actions (русский) |

---

## 📊 Статистика проекта

### Общая статистика

| Категория | Количество |
|-----------|-----------|
| **Всего файлов** | 55+ |
| **Kotlin файлов** | 35 |
| **XML файлов** | 5 |
| **Markdown файлов** | 6 |
| **YAML файлов** | 6 |
| **Gradle файлов** | 4 |

### По слоям архитектуры

| Слой | Файлов |
|------|--------|
| **Data Layer** | 10 |
| **Domain Layer** | 8 |
| **Presentation Layer** | 8 |
| **DI** | 3 |
| **Resources** | 5 |
| **Configuration** | 8 |
| **CI/CD** | 6 |
| **Documentation** | 6 |

### По типам файлов

| Тип | Количество | Назначение |
|-----|-----------|-----------|
| `.kt` | 35 | Kotlin исходный код |
| `.xml` | 5 | Android ресурсы и манифест |
| `.yml` | 6 | GitHub Actions workflows |
| `.md` | 6 | Документация |
| `.kts` | 3 | Gradle Kotlin DSL |
| `.pro` | 1 | ProGuard правила |
| `.properties` | 2 | Gradle конфигурация |
| `.bat` | 1 | Gradle wrapper (Windows) |

---

## 🗂️ Структура директорий

```
IR Remote/
├── .github/                    # GitHub конфигурация
│   ├── workflows/              # CI/CD workflows (5 файлов)
│   ├── dependabot.yml          # Dependabot конфигурация
│   ├── WORKFLOWS.md            # Документация (EN)
│   └── WORKFLOWS_RU.md         # Документация (RU)
│
├── app/                        # Модуль приложения
│   ├── src/main/
│   │   ├── java/com/irremote/app/
│   │   │   ├── data/           # Слой данных (10 файлов)
│   │   │   │   ├── local/      # Room БД (4 файла)
│   │   │   │   ├── mapper/     # Мапперы (2 файла)
│   │   │   │   ├── repository/ # Репозитории (2 файла)
│   │   │   │   └── service/    # Сервисы (1 файл)
│   │   │   │
│   │   │   ├── domain/         # Доменный слой (8 файлов)
│   │   │   │   ├── model/      # Модели (2 файла)
│   │   │   │   ├── repository/ # Интерфейсы (2 файла)
│   │   │   │   ├── service/    # Интерфейсы (1 файл)
│   │   │   │   └── usecase/    # Use Cases (3 файла)
│   │   │   │
│   │   │   ├── presentation/   # Слой представления (8 файлов)
│   │   │   │   ├── screen/     # Экраны (2 файла)
│   │   │   │   ├── navigation/ # Навигация (2 файла)
│   │   │   │   ├── components/ # Компоненты (1 файл)
│   │   │   │   ├── theme/      # Тема (3 файла)
│   │   │   │   └── MainActivity.kt
│   │   │   │
│   │   │   ├── di/             # DI модули (3 файла)
│   │   │   └── IRRemoteApplication.kt
│   │   │
│   │   ├── res/                # Ресурсы
│   │   │   ├── values/         # Строки, темы (2 файла)
│   │   │   └── xml/            # Backup правила (2 файла)
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   ├── build.gradle.kts        # Конфигурация модуля
│   └── proguard-rules.pro      # ProGuard правила
│
├── gradle/                     # Gradle wrapper
│   └── wrapper/
│       └── gradle-wrapper.properties
│
├── build.gradle.kts            # Корневая конфигурация
├── settings.gradle.kts         # Настройки проекта
├── gradle.properties           # Свойства Gradle
├── gradlew.bat                 # Gradle wrapper (Windows)
├── .gitignore                  # Git исключения
├── local.properties.template   # Шаблон local.properties
│
└── Документация/               # Документация проекта
    ├── README.md               # Основная документация
    ├── PROJECT_STRUCTURE.md    # Структура проекта
    ├── SUMMARY.md              # Краткое резюме
    ├── GETTING_STARTED.md      # Быстрый старт
    └── FILES_LIST.md           # Этот файл
```

---

## 🎯 Назначение по категориям

### 🏗️ Архитектура (Architecture)
- Clean Architecture с 3 слоями
- MVVM паттерн
- Repository паттерн
- Use Case паттерн

### 💾 Данные (Data)
- Room база данных (2 таблицы)
- DAO с Flow поддержкой
- Репозитории с мапперами
- ИК-передатчик сервис

### 🎯 Бизнес-логика (Domain)
- Доменные модели
- Интерфейсы репозиториев
- 18 Use Cases
- Enums для типов и протоколов

### 🎨 UI (Presentation)
- Jetpack Compose
- Material Design 3
- Navigation Compose
- ViewModels с StateFlow

### 🔧 Инфраструктура (Infrastructure)
- Hilt DI
- Gradle конфигурация
- ProGuard правила
- GitHub Actions CI/CD

### 📚 Документация (Documentation)
- README на русском
- Структура проекта
- Руководства
- CI/CD документация

---

## ✅ Чеклист файлов

### Обязательные файлы (созданы)
- [x] `build.gradle.kts` (root)
- [x] `settings.gradle.kts`
- [x] `gradle.properties`
- [x] `app/build.gradle.kts`
- [x] `AndroidManifest.xml`
- [x] `IRRemoteApplication.kt`
- [x] `MainActivity.kt`
- [x] `.gitignore`
- [x] `README.md`

### Архитектурные файлы (созданы)
- [x] Entities (2)
- [x] DAOs (2)
- [x] Database (1)
- [x] Mappers (2)
- [x] Repositories (2 impl + 2 interface)
- [x] Services (1 impl + 1 interface)
- [x] Models (2)
- [x] Use Cases (3 файла, 18 use cases)

### UI файлы (созданы)
- [x] Theme (3)
- [x] Navigation (2)
- [x] HomeScreen (2)
- [x] Components (1)

### DI файлы (созданы)
- [x] DatabaseModule
- [x] RepositoryModule
- [x] ServiceModule

### CI/CD файлы (созданы)
- [x] android-ci.yml
- [x] release.yml
- [x] code-quality.yml
- [x] pr-checks.yml
- [x] security.yml
- [x] dependabot.yml

### Документация (создана)
- [x] README.md
- [x] PROJECT_STRUCTURE.md
- [x] SUMMARY.md
- [x] GETTING_STARTED.md
- [x] WORKFLOWS.md
- [x] WORKFLOWS_RU.md
- [x] FILES_LIST.md

---

## 📝 Примечания

### Файлы не созданы (будут созданы автоматически)
- `local.properties` - создается Android Studio
- `gradle-wrapper.jar` - скачивается Gradle
- `build/` - создается при сборке
- `.idea/` - создается Android Studio

### Файлы для будущей разработки
- Остальные экраны (DeviceList, AddDevice, RemoteControl, Settings)
- Парсеры баз данных ИК-сигналов
- Сетевой слой (Retrofit API)
- Тесты (Unit, Integration, UI)

---

**Последнее обновление**: 30 апреля 2026  
**Всего файлов**: 55+  
**Статус**: Базовая структура завершена ✅
