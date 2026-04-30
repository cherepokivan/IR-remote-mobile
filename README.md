# IR Remote - Android приложение для управления устройствами через ИК-порт

![Android CI](https://github.com/cherepokivan/IR-remote-mobile/workflows/Android%20CI/badge.svg)
![Code Quality](https://github.com/cherepokivan/IR-remote-mobile/workflows/Code%20Quality/badge.svg)

## 📱 Описание проекта

Современное Android-приложение для управления различными устройствами (телевизоры, кондиционеры, вентиляторы, проекторы и т.д.) через инфракрасный порт смартфона. Приложение использует несколько открытых баз данных ИК-сигналов и предоставляет удобный интерфейс на Jetpack Compose.

## ✨ Основные возможности

- 📱 **Управление устройствами** через ИК-порт телефона
- 🗄️ **Большая база ИК-сигналов** для различных брендов (IRDB, Flipper-IRDB, IRremoteESP8266)
- 🔄 **Автоматическое обновление** баз данных при наличии интернета
- 📴 **Полная работа в офлайн-режиме** с локальным кэшированием
- 🎯 **Автоподбор модели** устройства
- 💾 **Сохранение пользовательских пультов**
- ⭐ **Избранные устройства** для быстрого доступа
- 🎨 **Material Design 3** с поддержкой динамических цветов
- 🌙 **Темная тема** (автоматическое переключение)

## 🎮 Поддерживаемые типы устройств

- 📺 Телевизоры (TV)
- ❄️ Кондиционеры (AC)
- 🌀 Вентиляторы (Fan)
- 🎥 Проекторы (Projector)
- 💿 DVD/Blu-ray плееры
- 🎵 Аудиосистемы
- 📡 Приставки (Set-top boxes)
- 🔧 Другие устройства

## 🗄️ Используемые базы данных ИК-сигналов

1. **IRDB** (https://github.com/probonopd/irdb)
   - Основная база данных с тысячами устройств
   - Поддержка множества брендов и моделей

2. **Flipper-IRDB** (https://github.com/Lucaslhm/Flipper-IRDB)
   - Дополнительная база от сообщества Flipper Zero
   - Актуальные сигналы для современных устройств

3. **IRremoteESP8266** (https://github.com/crankyoldgit/IRremoteESP8266)
   - Специализированная база для кондиционеров
   - Поддержка сложных протоколов AC

## 🛠️ Технологический стек

### Основные технологии
- **Язык**: Kotlin 1.9.22
- **UI**: Jetpack Compose + Material Design 3
- **Архитектура**: Clean Architecture + MVVM
- **Минимальная версия Android**: 5.0 (API 21)
- **Целевая версия Android**: 14 (API 34)

### Библиотеки и фреймворки
- **Dependency Injection**: Hilt 2.50
- **База данных**: Room 2.6.1
- **Сеть**: Retrofit 2.9.0 + OkHttp 4.12.0
- **Асинхронность**: Kotlin Coroutines 1.7.3 + Flow
- **Навигация**: Navigation Compose 2.7.7
- **Сериализация**: Gson 2.10.1
- **Обработка аннотаций**: KSP 1.9.22-1.0.17

### CI/CD
- **GitHub Actions**: Автоматическая сборка, тестирование, lint
- **Dependabot**: Автоматическое обновление зависимостей
- **CodeQL**: Анализ безопасности кода

## 📁 Структура проекта

```
IR Remote/
├── .github/
│   ├── workflows/              # GitHub Actions workflows
│   │   ├── android-ci.yml      # Основная CI сборка
│   │   ├── release.yml         # Релизные сборки
│   │   ├── code-quality.yml    # Проверка качества кода
│   │   ├── pr-checks.yml       # Проверки для PR
│   │   └── security.yml        # Сканирование безопасности
│   ├── dependabot.yml          # Настройки Dependabot
│   ├── WORKFLOWS.md            # Документация workflows (EN)
│   └── WORKFLOWS_RU.md         # Документация workflows (RU)
├── app/
│   ├── src/main/
│   │   ├── java/com/irremote/app/
│   │   │   ├── data/           # Слой данных
│   │   │   │   ├── local/      # Room база данных
│   │   │   │   │   ├── entity/ # Сущности БД
│   │   │   │   │   ├── dao/    # Data Access Objects
│   │   │   │   │   └── IRRemoteDatabase.kt
│   │   │   │   ├── mapper/     # Мапперы Entity ↔ Domain
│   │   │   │   ├── repository/ # Реализации репозиториев
│   │   │   │   └── service/    # Сервисы (IR передатчик)
│   │   │   ├── domain/         # Бизнес-логика
│   │   │   │   ├── model/      # Доменные модели
│   │   │   │   ├── repository/ # Интерфейсы репозиториев
│   │   │   │   ├── service/    # Интерфейсы сервисов
│   │   │   │   └── usecase/    # Use Cases
│   │   │   ├── presentation/   # UI слой
│   │   │   │   ├── screen/     # Экраны приложения
│   │   │   │   │   └── home/   # Главный экран
│   │   │   │   ├── navigation/ # Навигация
│   │   │   │   ├── components/ # Переиспользуемые компоненты
│   │   │   │   ├── theme/      # Тема приложения
│   │   │   │   └── MainActivity.kt
│   │   │   ├── di/             # Dependency Injection
│   │   │   │   ├── DatabaseModule.kt
│   │   │   │   ├── RepositoryModule.kt
│   │   │   │   └── ServiceModule.kt
│   │   │   └── IRRemoteApplication.kt
│   │   ├── res/                # Ресурсы
│   │   │   ├── values/
│   │   │   │   ├── strings.xml # Строки приложения
│   │   │   │   └── themes.xml  # Темы
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts        # Конфигурация модуля
│   └── proguard-rules.pro      # Правила ProGuard
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts            # Корневая конфигурация
├── settings.gradle.kts         # Настройки проекта
├── gradle.properties           # Свойства Gradle
├── gradlew.bat                 # Gradle wrapper (Windows)
├── .gitignore
├── README.md                   # Этот файл
└── PROJECT_STRUCTURE.md        # Детальная структура проекта
```

## 🏗️ Архитектура

Проект следует принципам **Clean Architecture** с разделением на три основных слоя:

### 1. Data Layer (Слой данных)
- **Entities**: Сущности Room базы данных
- **DAOs**: Интерфейсы доступа к данным
- **Repositories**: Реализации репозиториев
- **Mappers**: Преобразование Entity ↔ Domain моделей
- **Services**: Реализация сервисов (IR передатчик)

### 2. Domain Layer (Бизнес-логика)
- **Models**: Доменные модели (Device, IRCommand)
- **Repository Interfaces**: Контракты репозиториев
- **Service Interfaces**: Контракты сервисов
- **Use Cases**: Бизнес-логика приложения

### 3. Presentation Layer (UI)
- **ViewModels**: Управление состоянием UI
- **Screens**: Compose экраны
- **Components**: Переиспользуемые UI компоненты
- **Navigation**: Навигация между экранами
- **Theme**: Material Design 3 тема

## 🚀 Сборка проекта

### Требования
- Android Studio Hedgehog (2023.1.1) или новее
- JDK 17
- Android SDK 34
- Gradle 8.4
- Устройство с ИК-портом для тестирования

### Локальная сборка

```bash
# Клонировать репозиторий
git clone https://github.com/cherepokivan/IR-remote-mobile.git
cd IR-remote-mobile

# Собрать debug APK
./gradlew assembleDebug

# Собрать release APK
./gradlew assembleRelease

# Запустить тесты
./gradlew test

# Запустить lint
./gradlew lint
```

### Установка на устройство

```bash
# Установить debug версию
./gradlew installDebug

# Или через adb
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🔄 CI/CD через GitHub Actions

Проект использует GitHub Actions для автоматизации:

### Автоматические проверки
- ✅ Сборка проекта при каждом push
- ✅ Запуск unit тестов
- ✅ Проверка lint
- ✅ Анализ качества кода
- ✅ Сканирование безопасности
- ✅ Проверка размера APK

### Релизы
- 🏷️ Автоматическая сборка при создании тега `v*`
- 📦 Подпись APK (при настройке keystore)
- 🚀 Создание GitHub Release
- 📥 Загрузка APK как release asset

Подробнее: [.github/WORKFLOWS_RU.md](.github/WORKFLOWS_RU.md)

## 📋 Roadmap

### ✅ Выполнено
- [x] Базовая структура проекта
- [x] Интеграция с ИК-портом Android (ConsumerIrManager)
- [x] Room база данных (устройства и команды)
- [x] Clean Architecture + MVVM
- [x] Dependency Injection (Hilt)
- [x] Главный экран с избранными устройствами
- [x] Material Design 3 тема
- [x] Навигация (Navigation Compose)
- [x] GitHub Actions CI/CD

### 🚧 В разработке
- [ ] Экран списка устройств
- [ ] Экран добавления устройства
- [ ] Экран управления пультом (RemoteControl)
- [ ] Парсеры баз данных (IRDB, Flipper-IRDB, IRremoteESP8266)
- [ ] Сетевой слой для загрузки баз данных
- [ ] Автоподбор модели устройства
- [ ] Обучение ИК-сигналам
- [ ] Экран настроек

### 📅 Планируется
- [ ] Виджеты для быстрого доступа
- [ ] Макросы (последовательности команд)
- [ ] Экспорт/импорт пользовательских пультов
- [ ] Поддержка NFC меток
- [ ] Голосовое управление
- [ ] Интеграция с Google Assistant
- [ ] Unit и UI тесты

## 🧪 Тестирование

```bash
# Unit тесты
./gradlew test

# Instrumented тесты (требуется устройство/эмулятор)
./gradlew connectedAndroidTest

# Покрытие кода
./gradlew jacocoTestReport
```

## 📄 Лицензия

MIT License

Copyright (c) 2026

## 👨‍💻 Автор

Проект создан для личного использования и обучения современным практикам Android разработки.

## 🤝 Вклад в проект

Проект открыт для предложений и улучшений. Создавайте Issues и Pull Requests!

## 📚 Дополнительная документация

- [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - Детальная структура проекта
- [.github/WORKFLOWS_RU.md](.github/WORKFLOWS_RU.md) - Документация GitHub Actions
- [Документация Android IR API](https://developer.android.com/reference/android/hardware/ConsumerIrManager)

## 🔗 Полезные ссылки

- [IRDB Database](https://github.com/probonopd/irdb)
- [Flipper-IRDB](https://github.com/Lucaslhm/Flipper-IRDB)
- [IRremoteESP8266](https://github.com/crankyoldgit/IRremoteESP8266)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
