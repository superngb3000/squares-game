# Squares Web

Веб-приложение для игры «Квадраты» с визуальным интерфейсом.

## Запуск

```bash
npm install
npm start
```

По умолчанию клиент доступен на [http://localhost:3000](http://localhost:3000). 
Для полноценного функционирования требуется запущенный **squares-service** на `http://localhost:8080`.

## Возможности

- Настройка игроков (User / Computer) для каждого цвета (White / Black).
- Режимы:
  - User vs User
  - User vs Computer
  - Computer vs Computer
- Автоматический выбор ходов компьютером через API.

## Управление

- Щёлкайте по пустым клеткам, чтобы сделать ход.
- Компьютерные ходы рассчитываются автоматически через REST API.

## Технологии

- HTML, CSS, JavaScript
- Express.js
