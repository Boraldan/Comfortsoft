# Max Finder API

## Описание
**Max Finder API** — это REST-сервис на основе Spring Boot, который позволяет находить максимальное число в указанном Excel-файле с числами.

## Технологии
- Java 17+
- Spring Boot
- Spring Web
- Lombok
- Apache POI (для работы с Excel)
- CompletableFuture (асинхронная обработка запросов)
- OpenAPI (Swagger)

###  Сборка и запуск
```sh
mvn clean install
mvn spring-boot:run
```

## API Эндпоинты

### Найти максимальное число в Excel-файле
**POST** `/api/max`

#### Параметры запроса:
| Параметр | Тип | Описание |
|----------|-----|----------|
| N        | Long | Количество чисел, среди которых ищем максимум |
| filePath | String | Путь к Excel-файлу на сервере |

 
 
 