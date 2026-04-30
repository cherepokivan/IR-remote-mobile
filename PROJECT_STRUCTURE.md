# IR Remote - Структура проекта

## Обзор
Полноценное Android-приложение для управления устройствами через ИК-порт, построенное с использованием современных практик Android-разработки.

## Архитектура
- **Паттерн**: Clean Architecture + MVVM
- **Язык**: Kotlin
- **UI Framework**: Jetpack Compose с Material Design 3
- **Dependency Injection**: Hilt
- **База данных**: Room
- **Асинхронность**: Kotlin Coroutines + Flow

## Структура проекта

### 1. Слой приложения (Application Layer)
- `IRRemoteApplication.kt` - Класс приложения с инициализацией Hilt

### 2. Слой данных (Data Layer) - `data/`
#### Локальная база данных
- **Сущности (Entities)**:
  - `DeviceEntity` - Хранение информации об устройствах
  - `IRCommandEntity` - ИК-команды с внешним ключом к устройствам
  
- **DAO (Data Access Objects)**:
  - `DeviceDao` - CRUD операции для устройств с поддержкой Flow
  - `IRCommandDao` - Операции с ИК-командами
  
- **База данных**:
  - `IRRemoteDatabase` - Конфигурация Room базы данных

#### Репозитории
- `DeviceRepositoryImpl` - Операции с данными устройств
- `IRCommandRepositoryImpl` - Операции с данными ИК-команд

#### Мапперы
- `DeviceMapper` - Преобразование Entity ↔ Domain модели
- `IRCommandMapper` - Преобразование Entity ↔ Domain модели с JSON сериализацией паттернов

#### Сервисы
- `IRTransmitterServiceImpl` - Обертка над Android ConsumerIrManager для передачи ИК-сигналов

### 3. Доменный слой (Domain Layer) - `domain/`
#### Модели
- `Device` - Доменная модель устройства с enum DeviceType
- `IRCommand` - Модель ИК-команды с enum IRProtocol и списком паттернов

#### Репозитории (Интерфейсы)
- `DeviceRepository` - Контракт операций с устройствами
- `IRCommandRepository` - Контракт операций с ИК-командами

#### Сервисы (Интерфейсы)
- `IRTransmitterService` - Контракт передачи ИК-сигналов с типами ошибок

#### Use Cases (Варианты использования)
- **Устройства**: GetAllDevices, GetDeviceById, GetDevicesByType, GetFavoriteDevices, AddDevice, UpdateDevice, DeleteDevice, ToggleFavorite
- **ИК-команды**: GetCommandsByDeviceId, GetCommandById, GetCommandByName, AddCommand, AddCommands, UpdateCommand, DeleteCommand
- **ИК-передатчик**: TransmitIRCommand, CheckIRAvailability, GetCarrierFrequencies

### 4. Слой представления (Presentation Layer) - `presentation/`
#### Тема
- `Color.kt` - Цветовая палитра Material3
- `Type.kt` - Определения типографики
- `Theme.kt` - Конфигурация темы с динамическими цветами

#### Навигация
- `Screen.kt` - Sealed class с маршрутами навигации
- `AppNavigation.kt` - Конфигурация NavHost

#### Экраны
- **Главный экран (Home)**:
  - `HomeViewModel` - Управление состоянием главного экрана
  - `HomeScreen` - Главный экран с избранными и списком устройств

#### Компоненты
- `DeviceCard` - Переиспользуемая карточка устройства с иконкой и переключателем избранного

#### Главная активность
- `MainActivity` - Точка входа с настройкой навигации

### 5. Dependency Injection - `di/`
- `DatabaseModule` - Провайдеры Room базы данных и DAO
- `RepositoryModule` - Привязки репозиториев
- `ServiceModule` - Привязки сервисов

### 6. Ресурсы (Resources) - `res/`
- `values/strings.xml` - Все строки приложения на русском/английском
- `values/themes.xml` - Конфигурация Material темы
- `xml/data_extraction_rules.xml` - Правила резервного копирования для Android 12+
- `xml/backup_rules.xml` - Конфигурация резервного копирования (legacy)

### 7. Конфигурация сборки
- `build.gradle.kts` (корневой) - Версии плагинов
- `app/build.gradle.kts` - Конфигурация модуля приложения со всеми зависимостями
- `settings.gradle.kts` - Настройки проекта
- `gradle.properties` - Конфигурация Gradle
- `proguard-rules.pro` - Правила ProGuard для release сборок
- `gradle/wrapper/gradle-wrapper.properties` - Gradle 8.4 wrapper

### 8. Манифест
- `AndroidManifest.xml` - Требование ИК-оборудования, разрешения, конфигурация приложения

## Реализованные ключевые функции

### ✅ Завершено
1. **Слой базы данных**: Полная настройка Room с сущностями, DAO и базой данных
2. **Паттерн Repository**: Чистое разделение с мапперами
3. **Dependency Injection**: Полная настройка Hilt
4. **ИК-передача**: Интеграция ConsumerIrManager
5. **Use Cases**: Все доменные операции
6. **Главный экран**: Список устройств с избранными
7. **Навигация**: NavHost с определениями маршрутов
8. **Тема**: Material3 с динамическими цветами
9. **Система сборки**: Конфигурация Gradle с ProGuard

### 🚧 К реализации
1. **Экраны**:
   - DeviceListScreen - Список всех устройств
   - DeviceDetailScreen - Детали устройства
   - AddDeviceScreen - Добавление нового устройства
   - RemoteControlScreen - Основной интерфейс управления ИК-пультом
   - SettingsScreen - Настройки приложения

2. **Функции**:
   - Парсеры баз данных ИК-сигналов (IRDB, Flipper-IRDB, IRremoteESP8266)
   - Сетевой слой для загрузки баз данных ИК-сигналов
   - Автоопределение устройства
   - Конструктор пользовательских пультов
   - Обучение/запись ИК-сигналов

3. **Тестирование**:
   - Unit тесты
   - Интеграционные тесты
   - UI тесты

4. **CI/CD**:
   - Workflow GitHub Actions (уже создан)

## Зависимости
- Compose BOM 2024.02.00
- Hilt 2.50
- Room 2.6.1
- Retrofit 2.9.0
- OkHttp 4.12.0
- Gson 2.10.1
- Coroutines 1.7.3
- Navigation Compose 2.7.7

## Требования для сборки
- Android Studio Hedgehog или новее
- JDK 17
- Android SDK 34
- Gradle 8.4
- Kotlin 1.9.22

## Следующие шаги
1. Реализовать RemoteControlScreen с сеткой ИК-кнопок
2. Создать AddDeviceScreen с выбором бренда/модели
3. Реализовать парсеры баз данных ИК-сигналов
4. Добавить сетевой слой для загрузки баз данных
5. Создать функцию автоопределения устройства
6. Реализовать обучение ИК-сигналам
7. Добавить комплексное тестирование
8. Настроить CI/CD pipeline

## Примечания
- Требуется ИК-оборудование (`android.hardware.consumerir`)
- Минимальный SDK: 21 (Android 5.0)
- Целевой SDK: 34 (Android 14)
- ProGuard включен для release сборок
- База данных использует fallback к деструктивной миграции

## Детали реализации

### Слой данных (Data Layer)

#### Entities (Сущности)
```kotlin
// DeviceEntity - хранит информацию об устройстве
- id: Long (автогенерация)
- name: String (название устройства)
- type: String (тип: TV, AC, FAN и т.д.)
- brand: String (бренд)
- model: String? (модель, опционально)
- isFavorite: Boolean (в избранном)
- createdAt: Long (дата создания)
- updatedAt: Long (дата обновления)

// IRCommandEntity - хранит ИК-команды
- id: Long (автогенерация)
- deviceId: Long (внешний ключ к устройству)
- name: String (название команды: POWER, VOL_UP и т.д.)
- protocol: String (протокол: NEC, RC5, SAMSUNG и т.д.)
- frequency: Int (частота несущей в Гц, обычно 38000)
- pattern: String (JSON массив импульсов/пауз в микросекундах)
- createdAt: Long (дата создания)
```

#### DAOs (Data Access Objects)
Все DAO используют Flow для реактивных обновлений UI:
- `Flow<List<T>>` для списков (автоматическое обновление при изменении БД)
- `suspend fun` для одиночных операций (выполняются в корутинах)

#### Repositories
Репозитории преобразуют Entity в Domain модели через мапперы:
- Входящие данные: Domain модели → Entity (через `toEntity()`)
- Исходящие данные: Entity → Domain модели (через `toDomain()`)

### Доменный слой (Domain Layer)

#### Models (Модели)
```kotlin
// Device - доменная модель устройства
- Использует enum DeviceType вместо String
- Чистая бизнес-логика без зависимостей от Android

// IRCommand - доменная модель ИК-команды
- Использует enum IRProtocol
- pattern: List<Int> вместо JSON String
- Готова к использованию для передачи ИК-сигнала
```

#### Use Cases
Каждый Use Case выполняет одну конкретную задачу:
- Простые в тестировании
- Переиспользуемые
- Инкапсулируют бизнес-логику

### Слой представления (Presentation Layer)

#### ViewModels
- Используют StateFlow для управления состоянием UI
- Собирают данные из нескольких Use Cases
- Обрабатывают пользовательские действия

#### Compose UI
- Декларативный UI с Jetpack Compose
- Material Design 3 компоненты
- Поддержка динамических цветов (Android 12+)
- Автоматическое переключение темной темы

#### Навигация
- Type-safe навигация с sealed class
- Передача параметров через маршруты
- Поддержка deep links

### Dependency Injection (Hilt)

#### Модули
- **DatabaseModule**: @Singleton для базы данных и DAO
- **RepositoryModule**: @Binds для привязки интерфейсов к реализациям
- **ServiceModule**: @Binds для сервисов

#### Scope
- Все зависимости в SingletonComponent (живут весь жизненный цикл приложения)
- ViewModels автоматически получают зависимости через @HiltViewModel

## Паттерны и практики

### Clean Architecture
```
Presentation → Domain ← Data
     ↓           ↓        ↓
  ViewModel   UseCase  Repository
     ↓           ↓        ↓
   Screen    Interface  Implementation
```

### MVVM Pattern
```
View (Compose) → ViewModel → Model (Domain)
       ↑              ↓
       └─── StateFlow ───┘
```

### Repository Pattern
```
ViewModel → UseCase → Repository Interface
                            ↓
                    Repository Implementation
                            ↓
                      DAO / API Service
```

## Производительность

### Оптимизации
- **Flow**: Реактивные обновления без лишних запросов
- **Coroutines**: Асинхронные операции без блокировки UI
- **Room**: Эффективное кэширование и индексы
- **ProGuard**: Минификация и обфускация для release
- **Compose**: Умная перекомпозиция только измененных элементов

### Кэширование
- Room база данных для офлайн-режима
- Автоматическое обновление UI через Flow
- Минимальное количество запросов к БД

## Безопасность

### ProGuard Rules
- Сохранение Retrofit аннотаций
- Защита Gson сериализации
- Сохранение Room сущностей
- Обфускация кода в release

### Разрешения
- `INTERNET` - для загрузки баз данных
- `ACCESS_NETWORK_STATE` - проверка доступности сети
- `android.hardware.consumerir` - требование ИК-порта

## Тестирование (планируется)

### Unit Tests
- Use Cases
- ViewModels
- Repositories
- Mappers

### Integration Tests
- Room DAO
- Repository с реальной БД

### UI Tests
- Compose экраны
- Навигация
- Пользовательские сценарии
