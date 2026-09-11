# Bank API

API bancaria desarrollada con **Java 25 y Spring Boot 4**, orientada a la gestión de clientes, cuentas bancarias, transacciones y autenticación de usuarios.

Proyecto backend desarrollado aplicando buenas prácticas utilizadas en aplicaciones empresariales como:

* Arquitectura por capas.
* Separación de responsabilidades.
* DTO Pattern.
* Validaciones.
* Manejo global de excepciones.
* Persistencia con JPA/Hibernate.
* Migraciones con Flyway.
* Seguridad mediante JWT.
* Testing con JUnit 5 y Mockito.
* Contenerización con Docker.

---

# Descripción

Bank API es un proyecto backend que simula operaciones principales de un sistema bancario.

El objetivo del proyecto es construir una API escalable y mantenible aplicando principios de desarrollo profesional utilizados en entornos empresariales.

Actualmente cuenta con módulos para:

* Gestión de clientes.
* Gestión de cuentas bancarias.
* Gestión de transacciones financieras.
* Autenticación y autorización de usuarios.

---

# Tecnologías utilizadas

## Backend

* Java 25
* Spring Boot 4
* Spring Web
* Spring Data JPA
* Hibernate
* Spring Security
* JWT Authentication
* MapStruct
* Bean Validation
* Gradle

## Base de datos

* PostgreSQL 17
* Flyway Migration

## Testing

* JUnit 5
* Mockito

## Herramientas

* Docker
* Docker Compose
* Swagger/OpenAPI
* Git

---

# Requisitos

Para ejecutar el proyecto se requiere:

- Docker Desktop
- Git

La aplicación Spring Boot y PostgreSQL son ejecutados mediante Docker Compose.

No es necesario instalar directamente:

- JDK
- Gradle
- PostgreSQL

en la máquina local.

---

# Arquitectura del proyecto

El proyecto utiliza una arquitectura basada en capas:

```text
controller
     |
     ↓
service
     |
     ↓
repository
     |
     ↓
database
```

---

# Seguridad

La API implementa autenticación basada en JWT utilizando Spring Security.

Características:

* Login mediante usuario y contraseña.
* Generación de token JWT.
* Validación de token mediante filtro personalizado.
* Protección de endpoints privados.
* Implementación de UserDetailsService.

---

## Login

Endpoint:

```http
POST /api/v1/auth/login
```

Request:

```json
{
  "username": "admin",
  "password": "Admin123"
}
```

Response:

```json
{
  "token": "jwt-token"
}
```

El token generado permite acceder a endpoints protegidos.

---

# Base de datos y migraciones

El proyecto utiliza Flyway para administrar cambios en la estructura de base de datos.

Características:

* Versionamiento de scripts SQL.
* Creación automática de tablas.
* Control de historial de migraciones.
* Integración con Spring Boot.

Ubicación:

```text
src/main/resources/db/migration
```

---

# Ejecución con Docker

El proyecto utiliza Docker Compose para levantar:

* Aplicación Spring Boot.
* PostgreSQL 17.

Servicios:

```text
bank-api
postgres
```

Configuración PostgreSQL:

```text
Database: bank_db
Username: postgres
Password: postgres
Port: 5432
```

---

## Levantar contenedores

Ejecutar:

```bash
docker compose up
```

---

## Detener contenedores

```bash
docker compose down
```

---

## Persistencia de datos

PostgreSQL utiliza un volumen Docker:

```text
postgres_data
```

permitiendo conservar información aunque el contenedor sea detenido.

---

# Documentación API

La API cuenta con documentación interactiva mediante Swagger/OpenAPI.

Swagger permite:

* Visualizar endpoints disponibles.
* Ejecutar pruebas HTTP.
* Probar autenticación JWT.
* Revisar modelos Request/Response.

URL:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Módulos implementados

# Client

Gestión de clientes bancarios.

Funcionalidades:

* Crear cliente.
* Consultar clientes.
* Buscar cliente por ID.
* Actualizar información.
* Eliminación lógica mediante cambio de estado.

Estados:

```text
ACTIVE
BLOCKED
INACTIVE
```

---

# Account

Gestión de cuentas bancarias.

Funcionalidades:

* Crear cuentas.
* Consultar cuentas.
* Buscar cuenta por ID.
* Validación de cliente activo.
* Validación de cuentas duplicadas por tipo.

Tipos:

```text
SAVINGS
CHECKING
```

Estados:

```text
ACTIVE
BLOCKED
CLOSED
```

---

# Transaction

Gestión de movimientos financieros asociados a cuentas bancarias.

Funcionalidades:

* Registro de depósitos.
* Registro de retiros.
* Transferencias entre cuentas.
* Actualización de saldo.
* Historial de movimientos.

---

# Manejo global de excepciones

La API implementa manejo centralizado mediante:

```java
@RestControllerAdvice
```

Errores manejados:

| Código | Descripción |
| ------ | ----------- |
| 400 | Error de validación |
| 404 | Recurso no encontrado |
| 409 | Recurso duplicado |

Ejemplo:

```json
{
 "timestamp":"2026-09-11T10:00:00",
 "status":404,
 "error":"NOT FOUND",
 "message":"Client not found",
 "path":"/api/v1/clients/10"
}
```

---

# Testing

El proyecto implementa pruebas unitarias utilizando:

* JUnit 5.
* Mockito.

Actualmente se realizan pruebas sobre la capa Service.

Incluye:

* Validación de creación correcta.
* Validación de datos duplicados.
* Pruebas de excepciones.
* Verificación de comportamiento mediante mocks.

Ejemplo:

```text
ClientServiceTest
```

---

# Endpoints principales

## Authentication

Login:

```http
POST /api/v1/auth/login
```

---

## Clients

Obtener clientes:

```http
GET /api/v1/clients
```

Buscar cliente:

```http
GET /api/v1/clients/{id}
```

Crear cliente:

```http
POST /api/v1/clients
```

Actualizar cliente:

```http
PUT /api/v1/clients/{id}
```

Eliminar cliente:

```http
DELETE /api/v1/clients/{id}
```

---

## Accounts

Obtener cuentas:

```http
GET /api/v1/accounts
```

Buscar cuenta:

```http
GET /api/v1/accounts/{id}
```

Crear cuenta:

```http
POST /api/v1/accounts
```

---

## Transactions

Registrar movimiento:

```http
POST /api/v1/transactions
```

Consultar movimientos:

```http
GET /api/v1/transactions
```

Buscar movimiento por ID:

```http
GET /api/v1/transactions/{id}
```

---

# Próximos módulos

## Loan

Gestión de préstamos:

* Solicitudes.
* Evaluación.
* Estados.
* Desembolso.

---

## Card

Gestión de tarjetas:

* Creación.
* Estados.
* Asociación con cuentas.

---

# Control de versiones

El proyecto utiliza Git para control de cambios.

Convención utilizada:

```text
feat:
Nueva funcionalidad

fix:
Corrección de errores

refactor:
Mejoras internas

docs:
Cambios de documentación

test:
Pruebas automatizadas
```

---

# Autor

Joel Anderson Fernandez Pancorvo

Backend Developer Java

Proyecto desarrollado como portafolio profesional para fortalecer conocimientos en desarrollo backend con Java y Spring Boot.
