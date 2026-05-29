# Бабушкина рассада

Экономическая симуляция с игровыми элементами. Бабушка покупает семена, взращивает рассаду, продает урожай и соревнуется в таблице Forbes.

## Технологии

- Java 17
- Spring Boot 4.0.6
- Spring Data JPA (Hibernate)
- PostgreSQL / H2
- Docker & Docker Compose
- Maven
- Swagger/OpenAPI

## Сущности

- User (бабушка)
- Product (растение)
- Store (магазины семян)
- StoreProduct (цены и наличие)
- SeedPacket (купленные пакеты семян)
- Plant (растения со статусами SEED/SPROUT/MATURE/HARVESTED)

## Запуск

### Локально (IDEA, H2)

Профиль `dev` использует встроенную H2-базу, не требует установки PostgreSQL.

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

После запуска:

API — http://localhost:8080
Swagger UI — http://localhost:8080/swagger-ui/index.html
H2 Console — http://localhost:8080/h2-console

H2 Console параметры подключения:

JDBC URL: jdbc:h2:mem:garden_db
User Name: sa
Password: (оставить пустым)


Docker (PostgreSQL)

docker compose up --build

После запуска:

API — http://localhost:8081
Swagger UI — http://localhost:8081/swagger-ui/index.html

Остановка и удаление контейнеров с очисткой БД:

docker compose down -v

IDEA (Run Configuration)

Открыть GardenApplication.java

Добавить VM options: -Dspring.profiles.active=dev

Нажать Run

API Эндпоинты

POST /api/purchase/{userId}/{storeProductId}/{quantity} — Покупка семян
POST /api/plant/{userId}/{seedPacketId}/{quantity} — Посадка растений
POST /api/harvest/{plantId} — Сбор урожая
POST /api/sell/{plantId} — Продажа растения
POST /api/grow — Принудительное обновление статусов растений
GET /api/users/{userId}/plants — Список растений пользователя
GET /api/users/{userId}/balance — Баланс пользователя
GET /api/forbes — Таблица лидеров по балансу
GET /api/store-products/store/{storeId} — Ассортимент магазина
