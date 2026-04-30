# 🔧 Исправление ошибки Configuration Cache

## Что было не так

Gradle 8.4 с configuration cache не поддерживает запуск внешних процессов (git) во время конфигурации.

**Ошибка**:
```
Starting an external process 'git rev-list --count HEAD' during configuration time is unsupported.
```

## Решение

Вернул статичные версии в `build.gradle.kts`:
```kotlin
versionCode = 1
versionName = "1.0.0"
```

Версия будет меняться **только в имени релиза** на GitHub, а не в самом APK.

## Как это работает теперь

### В APK (статично):
- `versionCode`: 1 (всегда)
- `versionName`: "1.0.0" (всегда)

### В релизе на GitHub (динамично):
- Имя релиза: `v1.0.0-dev-20260430-202915`
- Имя APK: `IR-Remote-v1.0.0-dev-20260430-202915.apk`

## Если нужна динамическая версия в APK

Есть 3 варианта:

### Вариант 1: Отключить configuration cache
В `gradle.properties` добавить:
```properties
org.gradle.configuration-cache=false
```

### Вариант 2: Передавать версию через параметры
В GitHub Actions:
```yaml
- name: Build release APK
  run: |
    VERSION=$(git describe --tags --always)
    gradle assembleRelease -PversionName=$VERSION --no-daemon
```

В `build.gradle.kts`:
```kotlin
versionName = project.findProperty("versionName")?.toString() ?: "1.0.0"
```

### Вариант 3: Использовать gradle-git-properties плагин
Добавить в `build.gradle.kts`:
```kotlin
plugins {
    id("com.gorylenko.gradle-git-properties") version "2.4.1"
}
```

## Рекомендация

Оставить как есть - статичная версия в APK, динамическая в релизе. Это стандартный подход для CI/CD.

Пользователи скачивают APK с уникальным именем файла, а внутри APK версия стабильная.

---

**Статус**: Исправлено! Сборка снова работает. ✅
