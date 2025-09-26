# Squares Service

REST API для игры «Квадраты», построенный на основе движка (`squares-engine`).

## 🚀 Запуск

Для запуска необходимо предварительно собрать **service-engine**

```bash
cd ../squares-engine
gradlew build
```

И после запустить сам сервис

```bash
gradlew bootRun
```

По умолчанию сервис доступен на [http://localhost:8080](http://localhost:8080).

## 📡 Эндпоинты

### 1. `POST /api//nextMove`

Вычислить ход для текущего игрока.

#### Запрос

```json
{
  "size": 5,
  "data": "       b   w w   b       ",
  "nextPlayerColor": "b"
}
```

#### Ответ

```json
{
  "x": 0,
  "y": 2,
  "color": "B"
}
```

### 2. `POST /api/gameStatus`

Проверить состояние игры.

#### Запрос

```json
{
  "size": 5,
  "data": "       b   w w   b       "
}
```

#### Ответ

```json
{
  "status": "ONGOING",
  "playerColor": null
}
```

## 🛠️ Технологии

* Java 21
* Spring Boot 3
* Gradle
* Зависит от модуля **squares-engine** (движок игры)
