# Bank API

API bancaria desarrollada con Java y Spring Boot, orientada a la gestión de clientes, cuentas y operaciones financieras.

## Descripción

Bank API es un proyecto backend que simula las operaciones principales de un sistema bancario, aplicando buenas prácticas de desarrollo utilizadas en aplicaciones empresariales.

El proyecto busca construir una API escalable y mantenible aplicando:

* Arquitectura por capas.
* Separación de responsabilidades.
* DTOs para transferencia de información.
* Validaciones.
* Manejo global de excepciones.
* Persistencia con JPA/Hibernate.
* Mapeo automático con MapStruct.
* Contenerización del entorno con Docker.

---

# Tecnologías utilizadas

* Java (JDK 25)
* Spring Boot 4
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL 17
* MapStruct
* Gradle
* Docker
* Docker Compose
* Git

---

# Requisitos

Para ejecutar el proyecto se requiere:

* JDK 25
* Gradle
* Docker Desktop
* Git

La base de datos PostgreSQL se ejecuta mediante Docker Compose, por lo que no es necesario instalar PostgreSQL directamente en la máquina local.

---

# Configuración del entorno con Docker

El proyecto utiliza Docker Compose para levantar el servicio de PostgreSQL.

El archivo:

```text
docker-compose.yml
```

contiene la configuración del contenedor de base de datos.

Servicio utilizado:

```text
PostgreSQL 17
```

Configuración:

```text
Database: bank_db
Username: postgres
Password: postgres
Port: 5432
```

Para iniciar la base de datos ejecutar:

```bash
docker compose up
```

Para detener los contenedores:

```bash
docker compose down
```

Los datos de PostgreSQL se mantienen mediante un volumen:

```text
postgres_data
```

permitiendo conservar la información aunque el contenedor sea detenido.

---

# Arquitectura del proyecto

El proyecto utiliza una arquitectura en capas:

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

## Capas principales

### Controller

Responsable de exponer los endpoints REST y gestionar las solicitudes HTTP.

### Service

Contiene la lógica de negocio y las reglas del sistema.

### Repository

Encargado del acceso a datos mediante Spring Data JPA.

### Entity

Representa las entidades persistidas en la base de datos.

### DTO

Objetos utilizados para transportar información entre capas evitando exponer directamente las entidades.

### Mapper

Responsable de convertir entidades a DTOs y viceversa utilizando MapStruct.

---

# Ejecución del proyecto

Clonar el repositorio:

```bash
git clone <repository-url>
```

Ingresar al proyecto:

```bash
cd bank-api
```

Levantar la base de datos:

```bash
docker compose up
```

Ejecutar la aplicación:

```bash
./gradlew bootRun
```

---

# Módulos implementados

## Client

Gestión de clientes bancarios.

Funcionalidades:

* Crear cliente.
* Consultar clientes.
* Buscar cliente por ID.
* Actualizar información.
* Soft delete mediante cambio de estado.

Estados disponibles:

```text
ACTIVE
BLOCKED
INACTIVE
```

---

## Account

Gestión de cuentas bancarias.

Funcionalidades:

* Crear cuentas.
* Consultar cuentas.
* Buscar cuenta por ID.
* Validación de cliente activo.
* Validación de cuentas duplicadas por tipo.

Tipos de cuenta:

```text
SAVINGS
CHECKING
```

Estados disponibles:

```text
ACTIVE
BLOCKED
CLOSED
```

---

# Manejo global de errores

La API implementa manejo centralizado de excepciones mediante:

```java
@RestControllerAdvice
```

Errores manejados:

| Código HTTP | Descripción           |
| ----------- | --------------------- |
| 400         | Error de validación   |
| 404         | Recurso no encontrado |
| 409         | Recurso duplicado     |

Ejemplo de respuesta:

```json
{
    "timestamp": "2026-08-23T10:00:00",
    "status": 404,
    "error": "NOT FOUND",
    "message": "Client not found",
    "path": "/api/v1/clients/10"
}
```

---

# Endpoints principales

## Clients

### Obtener clientes

```http
GET /api/v1/clients
```

### Buscar cliente por ID

```http
GET /api/v1/clients/{id}
```

### Crear cliente

```http
POST /api/v1/clients
```

### Actualizar cliente

```http
PUT /api/v1/clients/{id}
```

### Eliminar cliente (Soft Delete)

```http
DELETE /api/v1/clients/{id}
```

---

## Accounts

### Obtener cuentas

```http
GET /api/v1/accounts
```

### Buscar cuenta por ID

```http
GET /api/v1/accounts/{id}
```

### Crear cuenta

```http
POST /api/v1/accounts
```

---

# Próximos módulos

## Transaction

Gestión de movimientos bancarios:

* Depósitos.
* Retiros.
* Transferencias.
* Historial de movimientos.
* Actualización de saldo.

## Loan

Gestión de préstamos:

* Solicitudes.
* Evaluación.
* Estados del préstamo.
* Desembolso.

## Card

Gestión de tarjetas:

* Creación de tarjetas.
* Estados.
* Relación con cuentas.

---

# Control de versiones

El proyecto utiliza Git para el control de cambios.

Convención de commits:

* `feat:` nuevas funcionalidades.
* `fix:` correcciones.
* `refactor:` mejoras internas.
* `docs:` documentación.
* `test:` pruebas.

---

# Autor

Joel Anderson Fernandez Pancorvo

Backend Developer Java

Proyecto realizado con fines de aprendizaje y construcción de portafolio profesional.
