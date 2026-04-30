# 📱 IR Remote - Информация о проекте

## Что это?

**IR Remote** - современное Android-приложение для управления бытовой техникой через инфракрасный порт смартфона. Превратите свой телефон в универсальный пульт дистанционного управления!

---

## ✨ Ключевые особенности

### 🎯 Для пользователей
- **Универсальный пульт** - управляйте телевизорами, кондиционерами, вентиляторами и другой техникой
- **Огромная база устройств** - тысячи моделей от сотен производителей
- **Автоподбор** - приложение само найдет вашу модель устройства
- **Работает офлайн** - не требует интернета после первой настройки
- **Избранное** - быстрый доступ к часто используемым устройствам
- **Современный дизайн** - Material Design 3 с поддержкой темной темы

### 🛠️ Для разработчиков
- **Clean Architecture** - чистая архитектура с разделением слоев
- **MVVM паттерн** - реактивный UI с ViewModel
- **Jetpack Compose** - современный декларативный UI
- **Hilt DI** - простая и мощная инъекция зависимостей
- **Room Database** - надежное локальное хранилище
- **Kotlin Coroutines** - асинхронность без боли
- **GitHub Actions** - автоматическая сборка и тесты

---

## 🎮 Поддерживаемые устройства

| Тип | Примеры | Статус |
|-----|---------|--------|
| 📺 Телевизоры | Samsung, LG, Sony, Philips | ✅ Поддерживается |
| ❄️ Кондиционеры | Daikin, Mitsubishi, Panasonic | ✅ Поддерживается |
| 🌀 Вентиляторы | Различные бренды | ✅ Поддерживается |
| 🎥 Проекторы | Epson, BenQ, Optoma | ✅ Поддерживается |
| 🔊 Аудиосистемы | Yamaha, Denon, Pioneer | ✅ Поддерживается |
| 📡 Приставки | Apple TV, Roku, Fire TV | ✅ Поддерживается |
| 💿 DVD/Blu-ray | Sony, Samsung, LG | ✅ Поддерживается |

---

## 📊 Статус проекта

### Текущая версия
```
Версия: 1.0.0-alpha
Дата: 30 апреля 2026
Статус: В активной разработке
Прогресс: 40% (базовая инфраструктура)
```

### Что работает ✅
- ✅ Архитектура проекта
- ✅ База данных (Room)
- ✅ Главный экран
- ✅ Навигация
- ✅ Material Design 3 тема
- ✅ CI/CD (GitHub Actions)

### В разработке 🚧
- 🚧 Экран управления устройством
- 🚧 Добавление устройств
- 🚧 Парсеры баз данных ИК-сигналов
- 🚧 Сетевой слой

### Запланировано 📋
- 📋 Автоподбор устройства
- 📋 Обучение ИК-сигналам
- 📋 Макросы (последовательности команд)
- 📋 Виджеты
- 📋 Экспорт/импорт

---

## 🏗️ Архитектура

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (Compose UI, ViewModels, Navigation)   │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Domain Layer                   │
│   (Models, Use Cases, Interfaces)       │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│           Data Layer                    │
│  (Room DB, Repositories, Services)      │
└─────────────────────────────────────────┘
```

### Слои
- **Presentation** - UI на Jetpack Compose, ViewModels с StateFlow
- **Domain** - Бизнес-логика, Use Cases, доменные модели
- **Data** - Room БД, репозитории, ИК-передатчик

---

## 🛠️ Технологии

### Основной стек
```kotlin
// UI
Jetpack Compose 2024.02.00
Material Design 3
Navigation Compose 2.7.7

// Архитектура
Clean Architecture
MVVM Pattern
Kotlin 1.9.22

// DI
Hilt 2.50

// База данных
Room 2.6.1

// Сеть
Retrofit 2.9.0
OkHttp 4.12.0

// Асинхронность
Coroutines 1.7.3
Flow

// Сборка
Gradle 8.4
KSP 1.9.22
```

---

## 📦 Структура проекта

```
IR Remote/
├── app/
│   └── src/main/java/com/irremote/app/
│       ├── data/           # Слой данных
│       │   ├── local/      # Room БД
│       │   ├── mapper/     # Мапперы
│       │   ├── repository/ # Репозитории
│       │   └── service/    # Сервисы
│       │
│       ├── domain/         # Доменный слой
│       │   ├── model/      # Модели
│       │   ├── repository/ # Интерфейсы
│       │   ├── service/    # Интерфейсы
│       │   └── usecase/    # Use Cases
│       │
│       ├── presentation/   # Слой представления
│       │   ├── screen/     # Экраны
│       │   ├── navigation/ # Навигация
│       │   ├── components/ # Компоненты
│       │   └── theme/      # Тема
│       │
│       └── di/             # DI модули
│
├── .github/workflows/      # CI/CD
└── docs/                   # Документация
```

---

## 🚀 Быстрый старт

### Для пользователей

1. **Скачайте APK** (когда будет доступен)
2. **Установите** на устройство с ИК-портом
3. **Добавьте устройство** через кнопку +
4. **Управляйте** своей техникой!

### Для разработчиков

```bash
# Клонировать репозиторий
git clone https://github.com/YOUR_USERNAME/IR-Remote.git

# Открыть в Android Studio
# File → Open → выбрать папку проекта

# Собрать проект
./gradlew build

# Запустить на устройстве
./gradlew installDebug
```

**Подробнее**: [GETTING_STARTED.md](GETTING_STARTED.md)

---

## 📚 Документация

### Основные документы
- 📖 [README.md](README.md) - Полное описание проекта
- 🏗️ [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - Детальная структура
- 📋 [SUMMARY.md](SUMMARY.md) - Краткое резюме
- 🚀 [GETTING_STARTED.md](GETTING_STARTED.md) - Быстрый старт
- 🗺️ [ROADMAP.md](ROADMAP.md) - План разработки
- 📂 [FILES_LIST.md](FILES_LIST.md) - Список всех файлов

### CI/CD документация
- 🤖 [.github/WORKFLOWS_RU.md](.github/WORKFLOWS_RU.md) - GitHub Actions (RU)
- 🤖 [.github/WORKFLOWS.md](.github/WORKFLOWS.md) - GitHub Actions (EN)

---

## 🎯 Roadmap

### Этап 1: MVP (2-3 недели)
- ✅ Базовая структура
- 🚧 Экран управления
- 🚧 Добавление устройств
- 🚧 Парсер IRDB

### Этап 2: Расширенный функционал (2-3 недели)
- 📋 Список устройств
- 📋 Детали устройства
- 📋 Сетевой слой
- 📋 Автоподбор
- 📋 Настройки

### Этап 3: Дополнительные парсеры (1-2 недели)
- 📋 Flipper-IRDB
- 📋 IRremoteESP8266
- 📋 Унификация

### Этап 4: Продвинутые функции (2-3 недели)
- 📋 Обучение ИК-сигналам
- 📋 Макросы
- 📋 Виджеты
- 📋 Экспорт/импорт

### Этап 5: Тестирование (1-2 недели)
- 📋 Unit тесты
- 📋 Integration тесты
- 📋 UI тесты
- 📋 Оптимизация

**Подробнее**: [ROADMAP.md](ROADMAP.md)

---

## 🗄️ Базы данных ИК-сигналов

### IRDB
- **Репозиторий**: https://github.com/probonopd/irdb
- **Формат**: CSV
- **Устройств**: 1000+
- **Статус**: ✅ Основная база

### Flipper-IRDB
- **Репозиторий**: https://github.com/Lucaslhm/Flipper-IRDB
- **Формат**: .ir файлы
- **Устройств**: 500+
- **Статус**: 📋 Запланировано

### IRremoteESP8266
- **Репозиторий**: https://github.com/crankyoldgit/IRremoteESP8266
- **Формат**: C++ headers
- **Устройств**: 200+ (кондиционеры)
- **Статус**: 📋 Запланировано

---

## 🤝 Вклад в проект

### Как помочь?

1. **Тестирование**
   - Тестируйте на разных устройствах
   - Сообщайте о багах

2. **Разработка**
   - Выберите задачу из Issues
   - Создайте Pull Request
   - Следуйте code style

3. **Документация**
   - Улучшайте документацию
   - Добавляйте примеры
   - Переводите на другие языки

4. **Базы данных**
   - Добавляйте новые устройства
   - Проверяйте существующие
   - Исправляйте ошибки

### Создание Pull Request

```bash
# 1. Создайте ветку
git checkout -b feature/my-feature

# 2. Внесите изменения
# ... код ...

# 3. Закоммитьте
git commit -m "feat: add my feature"

# 4. Запушьте
git push origin feature/my-feature

# 5. Создайте PR на GitHub
```

---

## 📊 Статистика

### Код
```
Файлов Kotlin:    35+
Строк кода:       2000+
Классов:          40+
Use Cases:        18
Экранов:          6 (1 готов)
```

### Архитектура
```
Слоев:            3 (Data, Domain, Presentation)
Модулей Hilt:     3
Репозиториев:     2
Сервисов:         1
```

### CI/CD
```
Workflows:        5
Jobs:             12+
Проверок:         Сборка, тесты, lint, безопасность
```

---

## 🔗 Полезные ссылки

### Проект
- 🌐 GitHub: https://github.com/YOUR_USERNAME/IR-Remote
- 📱 Google Play: (скоро)
- 💬 Issues: https://github.com/YOUR_USERNAME/IR-Remote/issues

### Android разработка
- 📖 [Jetpack Compose](https://developer.android.com/jetpack/compose)
- 📖 [Material Design 3](https://m3.material.io/)
- 📖 [Room Database](https://developer.android.com/training/data-storage/room)
- 📖 [Hilt DI](https://developer.android.com/training/dependency-injection/hilt-android)

### ИК-порт
- 📖 [ConsumerIrManager](https://developer.android.com/reference/android/hardware/ConsumerIrManager)
- 📖 [IR Remote Control](https://source.android.com/devices/tech/connect/infrared)

---

## 📄 Лицензия

```
MIT License

Copyright (c) 2026

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👨‍💻 Автор

Проект создан для личного использования и обучения современным практикам Android-разработки.

---

## 📞 Контакты

- 📧 Email: (добавьте свой email)
- 💬 Telegram: (добавьте свой telegram)
- 🐦 Twitter: (добавьте свой twitter)

---

## ⭐ Поддержите проект

Если вам нравится проект:
- ⭐ Поставьте звезду на GitHub
- 🐛 Сообщайте о багах
- 💡 Предлагайте новые функции
- 🔀 Создавайте Pull Requests
- 📢 Расскажите друзьям

---

## 🙏 Благодарности

- **IRDB** - за огромную базу ИК-сигналов
- **Flipper Zero Community** - за Flipper-IRDB
- **IRremoteESP8266** - за базу кондиционеров
- **Android Community** - за отличные библиотеки
- **Jetpack Compose Team** - за современный UI фреймворк

---

**Последнее обновление**: 30 апреля 2026  
**Версия**: 1.0.0-alpha  
**Статус**: 🚧 В активной разработке

---

<div align="center">

### 🚀 Готовы начать?

[📖 Читать документацию](README.md) • [🚀 Быстрый старт](GETTING_STARTED.md) • [🗺️ Roadmap](ROADMAP.md)

**Сделано с ❤️ для Android сообщества**

</div>
