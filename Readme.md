# 📦 Logistics Shipment Service

Este proyecto es una API RESTful para la gestión de envíos de una empresa de logística. Permite crear, consultar, listar y actualizar el estado de los envíos, así como consultar su ubicación y seguimiento.

---

## 🚀 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.x**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
- **Base de datos:** PostgreSQL
- **ORM:** Hibernate
- **Flyway** para migraciones de base de datos
- **Lombok** para reducir código boilerplate
- **Swagger / OpenAPI** para documentación interactiva
- **MapStruct** para transformación entre entidades y DTOs
- **Testcontainers** para pruebas de integración con PostgreSQL real
- **JUnit 5 + Mockito** para tests unitarios
- **Maven** para gestión del proyecto

---

## 📁 Estructura del proyecto

```
src/
├── main/
│   ├── java/com/company/logistics/
│   │   ├── api/                 # Controladores REST
│   │   ├── application/         # Casos de uso (servicios de aplicación)
│   │   ├── domain/              # Lógica de negocio
│   │   ├── infrastructure/      # Repositorios JPA, entidades, adaptadores externos
│   │   └── tracking/            # Servicio de tracking de envíos
│   └── resources/
│       ├── application.yml
│       └── db/migration/        # Scripts Flyway
└── test/
    ├── java/com/company/logistics/
    │   ├── api/                 # Tests unitarios
    │   ├── application/         # Tests unitarios
    │   └── IT/                  # Tests de integración
    └── resources/
        └── application-test.yml
```

🧪 Pruebas

✅ Tests Unitarios

Ubicados en src/test/java/com/company/logistics/application.
•	Usan JUnit 5 + Mockito para probar los servicios de aplicación y lógica de negocio.
•	No dependen de base de datos.

✅ Tests de Integración

Ubicados en src/test/java/com/company/logistics/IT.
•	Usan Testcontainers para levantar una base de datos PostgreSQL real durante los tests.
•	Las migraciones Flyway se ejecutan automáticamente.
•	Validan flujos end-to-end y consistencia con la base de datos.

Para ejecutar todos los tests:

```
mvn clean install
```
🐳 Cómo levantar el proyecto con Docker Compose

El proyecto incluye un archivo docker-compose.yml para levantar tanto la API como la base de datos PostgreSQL.

Paso 1: Compilar el proyecto

```
mvn clean package -DskipTests
```
Paso 2: Levantar contenedores
```
docker-compose up --build
```

Esto levanta:
•	La API de envíos (shipment-service) en el puerto 8089
•	PostgreSQL en el puerto 5432
•	Swagger UI disponible en http://localhost:8089/swagger-ui.html

Paso 3: Acceder a la base de datos

Puedes acceder a PostgreSQL con:
•	Host: localhost
•	Puerto: 5432
•	Usuario: postgres
•	Contraseña: postgres
•	Base de datos: logisticsdb


🔌 Probar WebSocket de seguimiento de envíos

La API expone un canal WebSocket para recibir actualizaciones en tiempo real del estado de los envíos.

📍 URL del 
```
ws://localhost:8080/ws/shipments
```
Asegúrate de que la API esté corriendo y que el puerto 8089 esté accesible desde tu máquina.

🧪 Cómo probarlo con Piehost WebSocket Tester
1.	Ve a https://piehost.com/websocket-tester.
2.	En el campo WebSocket URL, ingresa:
```
ws://localhost:8080/ws/shipments
```