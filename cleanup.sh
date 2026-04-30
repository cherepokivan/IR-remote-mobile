#!/bin/bash

# Скрипт для удаления старых пакетов из репозитория

echo "🔧 Удаление старых пакетов..."

# Удалить из git индекса
git rm -r --cached app/src/main/java/com/example/irremote 2>/dev/null || echo "com.example.irremote уже удалён"
git rm -r --cached app/src/main/java/com/irremote/app 2>/dev/null || echo "com.irremote.app уже удалён"

# Удалить физически (если остались)
rm -rf app/src/main/java/com/example 2>/dev/null || true
rm -rf app/src/main/java/com/irremote 2>/dev/null || true

echo "✅ Старые пакеты удалены"

# Добавить все изменения
git add .

# Показать статус
git status

echo ""
echo "📝 Теперь выполните:"
echo "git commit -m \"fix: remove duplicate packages and fix SettopBox icon\""
echo "git push"
