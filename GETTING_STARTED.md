# 🚀 Быстрый старт - IR Remote

## Для разработчиков

Это руководство поможет вам быстро начать работу с проектом IR Remote.

## 📋 Предварительные требования

### Обязательно
- **Android Studio**: Hedgehog (2023.1.1) или новее
- **JDK**: 17 или выше
- **Git**: Для клонирования репозитория

### Рекомендуется
- **Устройство с ИК-портом**: Для тестирования функционала
- **Android 12+**: Для тестирования динамических цветов

## 🔧 Установка

### 1. Клонирование репозитория

```bash
git clone https://github.com/YOUR_USERNAME/IR-Remote.git
cd IR-Remote
```

### 2. Открытие в Android Studio

1. Запустите Android Studio
2. File → Open
3. Выберите папку проекта `IR Remote`
4. Дождитесь синхронизации Gradle (первый раз может занять несколько минут)

### 3. Настройка SDK

Android Studio автоматически предложит установить необходимые компоненты:
- Android SDK 34
- Build Tools 34.0.0
- Android Emulator (опционально)

### 4. Первая сборка

```bash
# В терминале Android Studio или в корне проекта
./gradlew build
```

Или через Android Studio:
- Build → Make Project (Ctrl+F9)

## 🏃 Запуск приложения

### На реальном устройстве (рекомендуется)

1. Включите режим разработчика на Android устройстве:
   - Настройки → О телефоне → 7 раз нажать на "Номер сборки"
   
2. Включите отладку по USB:
   - Настройки → Для разработчиков → Отладка по USB

3. Подключите устройство к компьютеру

4. В Android Studio:
   - Run → Run 'app' (Shift+F10)
   - Выберите ваше устройство

### На эмуляторе

⚠️ **Внимание**: Эмулятор не имеет ИК-порта, поэтому функции передачи ИК-сигналов работать не будут.

1. Tools → Device Manager
2. Create Device
3. Выберите устройство (например, Pixel 6)
4. Выберите образ системы (рекомендуется API 34)
5. Run → Run 'app'

## 📁 Структура проекта

```
IR Remote/
├── app/src/main/java/com/irremote/app/
│   ├── data/              # Слой данных (БД, репозитории)
│   ├── domain/            # Бизнес-логика (модели, use cases)
│   ├── presentation/      # UI (экраны, компоненты)
│   └── di/                # Dependency Injection
├── .github/workflows/     # CI/CD конфигурация
└── docs/                  # Документация
```

## 🛠️ Основные команды Gradle

```bash
# Сборка debug APK
./gradlew assembleDebug

# Сборка release APK
./gradlew assembleRelease

# Запуск unit тестов
./gradlew test

# Запуск lint проверки
./gradlew lint

# Очистка проекта
./gradlew clean

# Установка на подключенное устройство
./gradlew installDebug
```

## 🎨 Разработка UI

### Jetpack Compose Preview

Для быстрой разработки UI используйте Preview:

```kotlin
@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    IRRemoteTheme {
        HomeScreen(
            onNavigateToDeviceList = {},
            onNavigateToDevice = {},
            onNavigateToAddDevice = {},
            onNavigateToSettings = {}
        )
    }
}
```

В Android Studio:
- Откройте файл с Compose компонентом
- Справа появится панель Preview
- Split/Design режим для одновременного просмотра кода и UI

## 🔍 Отладка

### Логирование

```kotlin
import android.util.Log

private const val TAG = "IRRemote"

Log.d(TAG, "Debug message")
Log.e(TAG, "Error message", exception)
```

### Отладка ИК-передачи

```kotlin
// В IRTransmitterServiceImpl.kt
override suspend fun transmit(frequency: Int, pattern: IntArray): Result<Unit> {
    Log.d(TAG, "Transmitting: frequency=$frequency, pattern size=${pattern.size}")
    // ...
}
```

### Проверка доступности ИК-порта

```kotlin
val irManager = getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
val hasIR = irManager?.hasIrEmitter() == true
Log.d(TAG, "IR available: $hasIR")
```

## 📊 Проверка базы данных

### Через Android Studio Database Inspector

1. Запустите приложение на устройстве/эмуляторе
2. View → Tool Windows → App Inspection
3. Выберите вкладку Database Inspector
4. Выберите процесс приложения
5. Просматривайте таблицы `devices` и `ir_commands`

### Через adb

```bash
# Получить доступ к shell устройства
adb shell

# Перейти в папку с БД
cd /data/data/com.irremote.app/databases/

# Открыть БД
sqlite3 ir_remote_database

# SQL команды
.tables                          # Список таблиц
SELECT * FROM devices;           # Все устройства
SELECT * FROM ir_commands;       # Все команды
.exit                            # Выход
```

## 🧪 Тестирование

### Unit тесты (когда будут добавлены)

```bash
# Запустить все тесты
./gradlew test

# Запустить тесты конкретного модуля
./gradlew :app:testDebugUnitTest

# С отчетом покрытия
./gradlew testDebugUnitTest jacocoTestReport
```

### UI тесты (когда будут добавлены)

```bash
# Требуется подключенное устройство или эмулятор
./gradlew connectedAndroidTest
```

## 🐛 Решение проблем

### Gradle sync failed

```bash
# Очистить кэш Gradle
./gradlew clean
./gradlew --stop

# В Android Studio
File → Invalidate Caches → Invalidate and Restart
```

### Build failed

1. Проверьте версию JDK (должна быть 17)
2. Проверьте Android SDK (должен быть установлен SDK 34)
3. Обновите Gradle wrapper:
   ```bash
   ./gradlew wrapper --gradle-version 8.4
   ```

### Hilt ошибки

Убедитесь, что:
1. Класс Application аннотирован `@HiltAndroidApp`
2. Activity аннотирована `@AndroidEntryPoint`
3. ViewModel аннотирована `@HiltViewModel`
4. Выполнена пересборка проекта: Build → Rebuild Project

### Room ошибки

```bash
# Очистить кэш KSP
./gradlew clean
rm -rf app/build/generated/ksp/
```

## 📝 Соглашения о коде

### Именование

```kotlin
// Классы: PascalCase
class DeviceRepository

// Функции и переменные: camelCase
fun getDeviceById()
val deviceList

// Константы: UPPER_SNAKE_CASE
const val MAX_DEVICES = 100

// Приватные поля: camelCase с префиксом _
private val _uiState = MutableStateFlow()
```

### Структура файлов

```kotlin
// 1. Package
package com.irremote.app.domain.model

// 2. Imports (сгруппированы и отсортированы)
import android.content.Context
import androidx.compose.runtime.*
import com.irremote.app.data.local.entity.DeviceEntity

// 3. Класс/интерфейс
class Device(...)
```

### Compose

```kotlin
// Composable функции: PascalCase
@Composable
fun DeviceCard(...)

// Preview функции: Preview + название
@Preview
@Composable
fun PreviewDeviceCard()
```

## 🔄 Git Workflow

### Создание новой ветки

```bash
# Для новой функции
git checkout -b feature/remote-control-screen

# Для исправления бага
git checkout -b fix/ir-transmission-error
```

### Коммиты

```bash
# Формат: тип(область): описание
git commit -m "feat(ui): add RemoteControlScreen"
git commit -m "fix(data): fix IR pattern parsing"
git commit -m "docs: update README"
```

Типы коммитов:
- `feat` - новая функция
- `fix` - исправление бага
- `docs` - документация
- `style` - форматирование кода
- `refactor` - рефакторинг
- `test` - тесты
- `chore` - обслуживание

### Pull Request

1. Создайте ветку
2. Внесите изменения
3. Запушьте в GitHub
4. Создайте Pull Request
5. Дождитесь прохождения CI/CD проверок

## 📚 Полезные ресурсы

### Документация проекта
- [README.md](README.md) - Основная документация
- [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - Структура проекта
- [SUMMARY.md](SUMMARY.md) - Краткое резюме
- [.github/WORKFLOWS_RU.md](.github/WORKFLOWS_RU.md) - CI/CD

### Android разработка
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt DI](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

### ИК-порт Android
- [ConsumerIrManager API](https://developer.android.com/reference/android/hardware/ConsumerIrManager)
- [IR Remote Control Guide](https://source.android.com/devices/tech/connect/infrared)

## 💬 Получить помощь

### Проблемы с проектом
- Создайте Issue на GitHub
- Опишите проблему подробно
- Приложите логи и скриншоты

### Вопросы по коду
- Проверьте документацию в коде (KDoc комментарии)
- Посмотрите примеры в существующих файлах
- Изучите тесты (когда будут добавлены)

## 🎯 Следующие шаги

После успешного запуска проекта:

1. **Изучите структуру**
   - Откройте [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)
   - Просмотрите основные классы в каждом слое

2. **Запустите приложение**
   - На реальном устройстве с ИК-портом
   - Проверьте главный экран

3. **Начните разработку**
   - Выберите задачу из [SUMMARY.md](SUMMARY.md)
   - Создайте новую ветку
   - Начните кодить! 🚀

## ✅ Чеклист первого запуска

- [ ] Клонирован репозиторий
- [ ] Открыт в Android Studio
- [ ] Gradle sync успешно завершен
- [ ] Проект собирается без ошибок
- [ ] Приложение запускается на устройстве/эмуляторе
- [ ] Главный экран отображается корректно
- [ ] Изучена документация проекта
- [ ] Создана первая ветка для разработки

---

**Готовы начать?** Выберите задачу из roadmap и создайте свою первую ветку! 🎉

**Вопросы?** Создайте Issue на GitHub или обратитесь к документации.
