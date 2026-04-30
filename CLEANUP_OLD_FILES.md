# Инструкция по очистке старых файлов

Выполните эти команды в Git Bash или терминале с git:

```bash
# Удалить старые пакеты из git индекса
git rm -r --cached app/src/main/java/com/example/irremote
git rm -r --cached app/src/main/java/com/irremote/app

# Закоммитить изменения
git add .
git commit -m "fix: remove duplicate packages, keep only ru.cherepokivan.irremote"
git push
```

Эти файлы больше не нужны, так как весь код перенесён в `ru.cherepokivan.irremote`.
