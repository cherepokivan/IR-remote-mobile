@echo off
REM Скрипт для удаления старых пакетов из репозитория (Windows)

echo 🔧 Удаление старых пакетов...

REM Удалить из git индекса
git rm -r --cached app/src/main/java/com/example/irremote 2>nul
git rm -r --cached app/src/main/java/com/irremote/app 2>nul

REM Удалить физически (если остались)
if exist "app\src\main\java\com\example" rmdir /s /q "app\src\main\java\com\example"
if exist "app\src\main\java\com\irremote" rmdir /s /q "app\src\main\java\com\irremote"

echo ✅ Старые пакеты удалены

REM Добавить все изменения
git add .

REM Показать статус
git status

echo.
echo 📝 Теперь выполните:
echo git commit -m "fix: remove duplicate packages and fix SettopBox icon"
echo git push

pause
