# IR Remote - Структура проекта

## Обзор
Полноценное Android-приложение для управления устройствами через ИК-порт, построенное с использованием современных практик Android-разработки.

## Архитектура
- **Паттерн**: Clean Architecture + MVVM
- **Язык**: Kotlin 1.9.22
- **UI Framework**: Jetpack Compose с Material Design 3
- **Dependency Injection**: Hilt 2.50
- **База данных**: Room 2.6.1
- **Асинхронность**: Kotlin Coroutines + Flow
- **Сеть**: Retrofit 2.9.0 + OkHttp 4.12.0
- **Android SDK**: 36 (Android 16)

## Структура директорий

```
app/src/main/java/ru/cherepokivan/irremote/
├── IRRemoteApplication.kt          # Точка входа приложения
├── data/                            # Слой данных
│   ├── local/                       # Локальная БД
│   │   ├── dao/                     # Data Access Objects
│   │   │   ├── DeviceDao.kt
│   │   │   └── IRCommandDao.kt
│   │   ├── entity/                  # Room сущности
│   │   │   ├── DeviceEntity.kt
│   │   │   └── IRCommandEntity.kt
│   │   ├── Converters.kt            # TypeConverters для Room
│   │   ├── IRRemoteDatabase.kt      # Конфигурация БД
│   │   └── SampleDataProvider.kt    # Тестовые данные
│   ├── remote/                      # Сетевой слой
│   │   ├── dto/                     # Data Transfer Objects
│   │   │   └── IRDBDeviceDto.kt
│   │   └── IRDBApi.kt               # Retrofit API
│   ├── mapper/                      # Мапперы DTO ↔ Domain
│   │   ├── DeviceMapper.kt
│   │   ├── IRCommandMapper.kt
│   │   └── IRDBMapper.kt
│   ├── repository/                  # Реализации репозиториев
│   │   ├── DeviceRepositoryImpl.kt
│   │   ├── IRCommandRepositoryImpl.kt
│   │   └── IRDBRepositoryImpl.kt
│   └── service/                     # Сервисы
│       └── IRTransmitterServiceImpl.kt
├── domain/                          # Доменный слой
│   ├── model/                       # Доменные модели
│   │   ├── Device.kt
│   │   └── IRCommand.kt
│   ├── repository/                  # Интерфейсы репозиториев
│   │   ├── DeviceRepository.kt
│   │   ├── IRCommandRepository.kt
│   │   └── IRDBRepository.kt
│   ├── service/                     # Интерфейсы сервисов
│   │   └── IRTransmitterService.kt
│   └── usecase/                     # Use Cases
│       ├── DeviceUseCases.kt
│       ├── IRCommandUseCases.kt
│       ├── IRDBUseCases.kt
│       └── IRTransmitterUseCases.kt
├── presentation/                    # Слой представления
│   ├── screen/                      # Экраны
│   │   ├── home/
│   │   │   ├── HomeScreen.kt
│   │   │   └── HomeViewModel.kt
│   │   ├── devicelist/
│   │   │   ├── DeviceListScreen.kt
│   │   │   └── DeviceListViewModel.kt
│   │   ├── adddevice/
│   │   │   ├── AddDeviceScreen.kt
│   │   │   └── AddDeviceViewModel.kt
│   │   ├── remote/
│   │   │   ├── RemoteControlScreen.kt
│   │   │   └── RemoteControlViewModel.kt
│   │   └── settings/
│   │       ├── SettingsScreen.kt
│   │       └── SettingsViewModel.kt
│   ├── components/                  # Переиспользуемые компоненты
│   │   └── DeviceCard.kt
│   ├── navigation/                  # Навигация
│   │   ├── Screen.kt
│   │   └── AppNavigation.kt
│   ├── theme/                       # Material Design 3 тема
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   └── Theme.kt
│   └── MainActivity.kt              # Главная активность
└── di/                              # Dependency Injection
    ├── DatabaseModule.kt
    ├── NetworkModule.kt
    ├── RepositoryModule.kt
    ├── ServiceModule.kt
    └── UseCaseModule.kt
```

## Ключевые компоненты

### Data Layer
- **Room Database**: Хранение устройств и команд с поддержкой Flow
- **Retrofit API**: Загрузка баз данных IRDB из GitHub
- **IR Transmitter Service**: Работа с ConsumerIrManager + проприетарные API (Xiaomi, Samsung)

### Domain Layer
- **Models**: Device, IRCommand с enum типами
- **Use Cases**: Инкапсуляция бизнес-логики
- **Repositories**: Абстракции для доступа к данным

### Presentation Layer
- **Jetpack Compose**: Декларативный UI
- **ViewModels**: Управление состоянием с StateFlow
- **Navigation Compose**: Навигация между экранами
- **Material Design 3**: Современный дизайн с динамическими цветами

### Dependency Injection
- **Hilt**: Автоматическая генерация DI кода
- **Модули**: Разделение по слоям (Database, Network, Repository, Service, UseCase)

## Конфигурация

### Build Configuration
- **compileSdk**: 36 (Android 16)
- **minSdk**: 21 (Android 5.0)
- **targetSdk**: 36 (Android 16)
- **Gradle**: 8.4
- **JDK**: 17

### Dependencies
- Compose BOM 2024.02.00
- Hilt 2.50
- Room 2.6.1
- Retrofit 2.9.0
- OkHttp 4.12.0
- Coroutines 1.7.3
- Navigation Compose 2.7.7

### CI/CD
- **GitHub Actions**: Автоматическая сборка при каждом push
- **Workflows**: Build, Test, Lint, Security, Release
- **Releases**: Автоматическое создание релизов с APK

## Разрешения

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.TRANSMIT_IR" />
<uses-feature android:name="android.hardware.consumerir" android:required="false" />
```

## Особенности реализации

### IR Передача
- Стандартный ConsumerIrManager API
- Fallback на проприетарные API через reflection (Xiaomi, Samsung)
- Логирование для диагностики

### База данных
- Загрузка 3 популярных устройств (Samsung, LG, Sony TV)
- Локальное кэширование
- Поддержка офлайн режима

### UI/UX
- Material Design 3 с динамическими цветами
- Темная тема
- Адаптивные иконки
- Плавные анимации

---

**Последнее обновление**: 01 мая 2026  
**Версия**: 1.0.3
