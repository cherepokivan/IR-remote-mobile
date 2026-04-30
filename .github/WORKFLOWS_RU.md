# Документация GitHub Actions CI/CD

## Обзор
Проект использует GitHub Actions для непрерывной интеграции, развертывания и проверки качества кода.

## Рабочие процессы (Workflows)

### 1. Android CI (`android-ci.yml`)
**Триггеры**: Push и PR в ветки `main` и `develop`

**Задачи**:
- **build**: Компилирует проект, запускает тесты и собирает debug APK
- **lint**: Запускает проверку Android lint и загружает результаты

**Артефакты**:
- `app-debug.apk` - Debug сборка
- `lint-results` - Отчеты анализа lint

### 2. Release Build (`release.yml`)
**Триггеры**: Push тегов вида `v*` (например, `v1.0.0`)

**Задачи**:
- **release**: Собирает release APK, подписывает его (если настроен keystore) и создает GitHub release

**Артефакты**:
- `app-release.apk` - Подписанный release APK

**Необходимые секреты** (опционально, для подписи):
- `KEYSTORE_FILE` - Keystore файл в base64
- `KEY_ALIAS` - Алиас ключа
- `KEYSTORE_PASSWORD` - Пароль keystore
- `KEY_PASSWORD` - Пароль ключа

### 3. Code Quality (`code-quality.yml`)
**Триггеры**: Push и PR в ветки `main` и `develop`

**Задачи**:
- **test**: Запускает unit тесты и генерирует отчеты
- **ktlint**: Проверяет стиль кода Kotlin (опционально)
- **detekt**: Запускает статический анализ кода (опционально)

**Артефакты**:
- `test-results` - Результаты JUnit тестов
- `detekt-report` - Отчет статического анализа

### 4. Pull Request Checks (`pr-checks.yml`)
**Триггеры**: PR открыт, синхронизирован или переоткрыт

**Задачи**:
- **validate**: Проверяет Gradle wrapper, форматирование, запускает lint и тесты
- **size-check**: Измеряет размер APK и комментирует в PR

**Возможности**:
- Автоматические комментарии в PR со статусом сборки
- Отслеживание размера APK

### 5. Security Scan (`security.yml`)
**Триггеры**: 
- Еженедельно (понедельник в полночь)
- Push в `main`
- PR в `main`

**Задачи**:
- **dependency-check**: Сканирует уязвимые зависимости
- **codeql**: Анализ безопасности GitHub CodeQL
- **gradle-scan**: Проверяет доступные обновления зависимостей

**Артефакты**:
- `dependency-check-report` - Отчет об уязвимостях
- `dependency-updates` - Отчет о доступных обновлениях

### 6. Dependabot (`dependabot.yml`)
**Расписание**: Еженедельно по понедельникам в 09:00

**Обновления**:
- Gradle зависимости
- Версии GitHub Actions

**Настройки**:
- Максимум 10 PR для Gradle зависимостей
- Максимум 5 PR для GitHub Actions
- Авто-метки: `dependencies`, `gradle`, `github-actions`

## Инструкции по настройке

### 1. Включение GitHub Actions
Actions включены по умолчанию. Workflows запустятся автоматически при триггерах.

### 2. Настройка секретов (Опционально - для подписи Release)
Перейдите в Settings → Secrets and variables → Actions:

```bash
# Создать keystore (если у вас его нет)
keytool -genkey -v -keystore release.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000

# Конвертировать keystore в base64
base64 release.keystore > keystore.base64

# Добавить секреты в GitHub:
KEYSTORE_FILE=<содержимое keystore.base64>
KEY_ALIAS=release
KEYSTORE_PASSWORD=<ваш пароль keystore>
KEY_PASSWORD=<ваш пароль ключа>
```

### 3. Правила защиты веток (Рекомендуется)
Settings → Branches → Добавить правило для `main`:
- ✅ Требовать прохождение проверок перед слиянием
- ✅ Требовать актуальность веток перед слиянием
- Выбрать: `build`, `lint`, `test`, `validate`

### 4. Включить Dependabot Alerts
Settings → Security → Dependabot alerts → Enable

## Бейджи статуса workflows

Добавьте в README.md:

```markdown
![Android CI](https://github.com/ВАШ_USERNAME/IR-Remote/workflows/Android%20CI/badge.svg)
![Code Quality](https://github.com/ВАШ_USERNAME/IR-Remote/workflows/Code%20Quality/badge.svg)
![Security Scan](https://github.com/ВАШ_USERNAME/IR-Remote/workflows/Security%20Scan/badge.svg)
```

## Создание релиза

### Ручной релиз
```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### Автоматический релиз
Workflow `release.yml` выполнит:
1. Сборку release APK
2. Подпись APK (если настроены секреты)
3. Создание GitHub release
4. Загрузку APK как release asset

## Устранение неполадок

### Сборка не удалась
- Проверьте версию JDK (должна быть 17)
- Убедитесь, что Gradle wrapper исполняемый
- Проверьте версии зависимостей

### Подпись не удалась
- Проверьте, что все секреты установлены правильно
- Проверьте пароль keystore
- Убедитесь, что keystore закодирован в base64

### Тесты не прошли
- Запустите тесты локально: `./gradlew test`
- Проверьте отчеты тестов в артефактах

### Ошибки Lint
- Запустите локально: `./gradlew lint`
- Исправьте проблемы или обновите lint baseline

## Локальное тестирование

Тестируйте workflows локально с помощью [act](https://github.com/nektos/act):

```bash
# Установить act
brew install act  # macOS
choco install act  # Windows

# Запустить workflow
act -j build
act -j test
```

## Оптимизация производительности

### Кэширование
Все workflows используют кэширование Gradle для ускорения сборки:
```yaml
cache: gradle
```

### Параллельные задачи
Независимые задачи выполняются параллельно:
- `build` и `lint` запускаются одновременно
- `test`, `ktlint` и `detekt` выполняются параллельно

### Условное выполнение
Некоторые шаги выполняются только при необходимости:
- Подпись только если существуют секреты
- Загрузка артефактов только при неудаче/успехе

## Мониторинг

### Просмотр запусков Workflow
Репозиторий → Actions → Выбрать workflow

### Скачать артефакты
Запуск workflow → Раздел Artifacts → Скачать

### Проверить логи
Запуск workflow → Выбрать задачу → Просмотр логов

## Лучшие практики

1. **Быстрые workflows**: Используйте кэширование, параллельные задачи
2. **Быстрый отказ**: Запускайте быстрые проверки первыми (lint, format)
3. **Понятные имена**: Четкие названия задач и шагов
4. **Хранение артефактов**: Установите подходящие периоды хранения
5. **Безопасность**: Никогда не коммитьте секреты, используйте GitHub Secrets
6. **Документация**: Поддерживайте этот файл в актуальном состоянии

## Ресурсы

- [Документация GitHub Actions](https://docs.github.com/ru/actions)
- [Лучшие практики Android CI/CD](https://developer.android.com/studio/build/building-cmdline)
- [Gradle Build Cache](https://docs.gradle.org/current/userguide/build_cache.html)
