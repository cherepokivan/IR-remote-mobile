# 🚀 Инструкция по загрузке проекта на GitHub

## Быстрый старт

Проект готов к загрузке на GitHub. GitHub Actions автоматически соберет APK при каждом push.

---

## 📋 Шаг 1: Создайте репозиторий на GitHub

1. Перейдите на https://github.com/new
2. Название: `IR-Remote` (или любое другое)
3. Описание: `Android IR remote control app`
4. Выберите: **Public** или **Private**
5. **НЕ** добавляйте README, .gitignore, license (уже есть в проекте)
6. Нажмите **Create repository**

---

## 📋 Шаг 2: Инициализируйте Git локально

Откройте PowerShell в папке проекта:

```powershell
cd "C:\Users\chere\OneDrive\Documentos\Проэкты claude\IR Remote"

# Инициализация Git
git init

# Добавить все файлы
git add .

# Первый коммит
git commit -m "feat: initial commit - complete IR Remote app with all screens"

# Переименовать ветку в main (если нужно)
git branch -M main
```

---

## 📋 Шаг 3: Подключите удаленный репозиторий

Замените `YOUR_USERNAME` на ваш GitHub username:

```powershell
git remote add origin https://github.com/YOUR_USERNAME/IR-Remote.git
```

---

## 📋 Шаг 4: Загрузите на GitHub

```powershell
git push -u origin main
```

При первом push GitHub может попросить авторизацию:
- Используйте Personal Access Token (рекомендуется)
- Или GitHub Desktop для упрощения

---

## 🤖 Шаг 5: GitHub Actions автоматически запустится

После push:

1. **Перейдите в Actions** на GitHub
2. **Увидите запущенные workflows**:
   - ✅ Android CI (сборка + тесты)
   - ✅ Code Quality (проверка кода)
   - ✅ Security Scan (безопасность)

3. **Дождитесь завершения** (~5-10 минут)

4. **Скачайте APK**:
   - Откройте успешный workflow run
   - Найдите раздел **Artifacts**
   - Скачайте `app-debug.apk`

---

## 📱 Шаг 6: Установите APK на устройство

### Вариант A: Через ADB (если подключен компьютер)

```powershell
adb install app-debug.apk
```

### Вариант B: Прямая установка

1. Скачайте APK на телефон
2. Откройте файл
3. Разрешите установку из неизвестных источников
4. Установите

---

## 🎉 Готово!

Приложение установлено и готово к использованию:

- ✅ 3 тестовых устройства загрузятся автоматически
- ✅ Можно сразу тестировать управление
- ✅ Можно добавлять свои устройства

---

## 🔧 Дополнительные команды Git

### Проверить статус
```powershell
git status
```

### Добавить изменения
```powershell
git add .
git commit -m "feat: add new feature"
git push
```

### Создать новую ветку
```powershell
git checkout -b feature/new-feature
git push -u origin feature/new-feature
```

### Создать релиз (автоматическая сборка release APK)
```powershell
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

После push тега GitHub Actions:
- Соберет release APK
- Создаст GitHub Release
- Прикрепит APK к релизу

---

## 📊 Что будет происходить при каждом push

### Автоматически запустятся:

1. **Android CI** (`android-ci.yml`)
   - Сборка проекта
   - Запуск тестов
   - Lint проверка
   - Создание APK артефакта

2. **Code Quality** (`code-quality.yml`)
   - Unit тесты
   - Проверка стиля кода
   - Статический анализ

3. **Security Scan** (`security.yml`)
   - Проверка зависимостей
   - CodeQL анализ
   - Поиск уязвимостей

### При создании Pull Request:

4. **PR Checks** (`pr-checks.yml`)
   - Все проверки выше
   - Проверка размера APK
   - Автоматический комментарий с результатами

### При создании тега (v*):

5. **Release Build** (`release.yml`)
   - Сборка release APK
   - Подпись APK (если настроены секреты)
   - Создание GitHub Release
   - Загрузка APK в релиз

---

## 🔐 Настройка подписи APK (опционально)

Для подписи release APK нужно добавить секреты в GitHub:

### 1. Создайте keystore (если нет)

```powershell
keytool -genkey -v -keystore release.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000
```

### 2. Конвертируйте в base64

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("release.keystore")) | Out-File keystore.base64
```

### 3. Добавьте секреты в GitHub

Settings → Secrets and variables → Actions → New repository secret:

- `KEYSTORE_FILE` = содержимое keystore.base64
- `KEY_ALIAS` = release
- `KEYSTORE_PASSWORD` = ваш пароль keystore
- `KEY_PASSWORD` = ваш пароль ключа

После этого release APK будет автоматически подписываться.

---

## 📝 Структура коммитов

Используйте conventional commits:

```
feat: добавить новую функцию
fix: исправить баг
docs: обновить документацию
style: форматирование кода
refactor: рефакторинг
test: добавить тесты
chore: обслуживание
```

Примеры:
```powershell
git commit -m "feat(ui): add device detail screen"
git commit -m "fix(ir): fix transmission error handling"
git commit -m "docs: update README with new features"
```

---

## 🐛 Решение проблем

### Ошибка: "failed to push some refs"

```powershell
git pull origin main --rebase
git push
```

### Ошибка: "Permission denied"

Используйте Personal Access Token:
1. GitHub → Settings → Developer settings → Personal access tokens
2. Generate new token (classic)
3. Выберите scopes: `repo`, `workflow`
4. Используйте токен вместо пароля

### GitHub Actions не запускается

1. Проверьте, что workflows в папке `.github/workflows/`
2. Проверьте, что файлы имеют расширение `.yml`
3. Проверьте синтаксис YAML

---

## 📚 Полезные ссылки

- [GitHub Docs](https://docs.github.com)
- [Git Basics](https://git-scm.com/book/en/v2/Getting-Started-Git-Basics)
- [GitHub Actions](https://docs.github.com/en/actions)
- [Conventional Commits](https://www.conventionalcommits.org/)

---

## ✅ Чеклист перед push

- [ ] Все файлы добавлены (`git add .`)
- [ ] Коммит создан с понятным сообщением
- [ ] Проверен .gitignore (не загружаются лишние файлы)
- [ ] Удален local.properties (если есть)
- [ ] Проверена документация (актуальна)

---

## 🎊 После успешного push

1. ✅ Проект на GitHub
2. ✅ GitHub Actions работает
3. ✅ APK собирается автоматически
4. ✅ Можно скачать и установить
5. ✅ Можно делиться ссылкой на репозиторий

---

**Готово! Проект на GitHub и готов к использованию! 🚀**

---

**Дата**: 30 апреля 2026  
**Версия**: 1.0.0-alpha  
**Статус**: ✅ Готов к деплою
