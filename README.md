<div align="center">

# CareMe — Backend API

**Plataforma de conexión entre cuidadores profesionales y familias**

*Desarrollado por [MediTec](https://github.com/Fabriziolara17) · Universidad Peruana de Ciencias Aplicadas · SI385*

---

![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.13-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=flat-square&logo=swagger&logoColor=black)
![AWS](https://img.shields.io/badge/Deploy-AWS-FF9900?style=flat-square&logo=amazonaws&logoColor=white)

</div>

---

## Tabla de Contenidos

- [Descripcion del Proyecto](#descripcion-del-proyecto)
- [Funcionalidades](#funcionalidades)
- [Arquitectura](#arquitectura)
- [Tech Stack](#tech-stack)
- [Modelo de Dominio](#modelo-de-dominio)
- [Instalacion y Configuracion](#instalacion-y-configuracion)
- [Documentacion de la API](#documentacion-de-la-api)
- [Endpoints Principales](#endpoints-principales)
- [Equipo](#equipo)

---

## Descripcion del Proyecto

**CareMe** es el producto principal de la startup **MediTec**. Es una plataforma digital que conecta a **familias** que necesitan cuidado especializado para sus pacientes con **cuidadores profesionales** verificados.

El sistema resuelve la dificultad que enfrentan las familias peruanas para encontrar cuidadores de confianza, proporcionando un ecosistema completo que abarca desde la busqueda y contratacion del cuidador hasta el seguimiento del servicio, los pagos y la gestion de recordatorios de medicacion.

### Actores del sistema

| Actor | Descripcion |
|---|---|
| **Familiar** | Contrata servicios de cuidado para sus pacientes |
| **Cuidador** | Ofrece servicios de cuidado profesional |
| **Paciente** | Persona que recibe el servicio de cuidado |
| **Administrador** | Gestiona y supervisa la plataforma |

---

## Funcionalidades

### Autenticacion y Usuarios
- Registro e inicio de sesion con contrasena hasheada (BCrypt)
- Recuperacion de contrasena por correo o telefono con token temporal
- Soporte para autenticacion local y proveedores externos

### Gestion de Cuidadores
- Perfiles con especialidad, ubicacion, tarifa base y disponibilidad
- Verificacion de certificados profesionales
- Gestion de horarios disponibles
- Calificacion promedio calculada automaticamente

### Contratacion de Servicios
- Solicitud de servicio por parte del familiar
- Tipos de servicio: basico y premium
- Calculo automatico de costo total (tarifa base + recargohorario - descuento)
- Estados del servicio: `SOLICITADO` → `ACEPTADO` → `EN_CURSO` → `FINALIZADO`

### Pagos
- Integracion con **Yape** (codigo de operacion + telefono)
- Procesamiento de pagos con multiples metodos
- Generacion de comprobante de pago
- Cambio automatico de estado del servicio al confirmar pago

### Seguimiento Clinico
- Condiciones medicas asociadas a pacientes y cuidadores
- **Recordatorios de medicacion** con scheduler automatizado
- Evaluaciones del servicio de cuidado

### Comunicacion y Valoraciones
- Sistema de mensajeria entre usuarios
- Calificaciones y resenas del servicio
- Consejos de salud publicados por el administrador

### Administracion
- Reportes del administrador
- Gestion de reclamos
- Supervision de cuidadores (flag `observado`)
- Panel de control para consejos y condiciones medicas

---

## Arquitectura

El proyecto sigue una arquitectura en **capas** (Layered Architecture) tipica de aplicaciones Spring Boot:

```
src/main/java/com/upc/careme/
│
├── config/                 # Configuracion global (CORS, Security, ModelMapper)
├── controllers/            # Capa de presentacion — REST endpoints
├── services/               # Capa de negocio — logica de la aplicacion
├── repositorios/           # Capa de datos — Spring Data JPA
├── entidades/              # Modelos JPA — tablas de la base de datos
└── dtos/                   # Data Transfer Objects — contratos de la API
```

Cada recurso sigue el patron: `Controller → Service → Repository → Entity`

---

## Tech Stack

| Categoria | Tecnologia | Version |
|---|---|---|
| Lenguaje | Java | 17 |
| Framework principal | Spring Boot | 3.5.13 |
| Persistencia | Spring Data JPA + Hibernate | — |
| Base de datos | PostgreSQL | 15+ |
| Seguridad | Spring Security + BCrypt | — |
| Documentacion API | SpringDoc OpenAPI (Swagger UI) | 2.7.0 |
| Mapeo de objetos | ModelMapper | 3.1.1 |
| Utilidades | Lombok | — |
| Build | Apache Maven | 3.9+ |
| Despliegue | Amazon Web Services (AWS) | — |

---

## Modelo de Dominio

```
Usuario (base de todos los actores)
 ├── Familiar ──────────────────── contrata ──────► Servicio
 ├── Cuidador ──────────────────── presta   ──────► Servicio
 │     ├── HorarioCuidador
 │     ├── CertificadoCuidador
 │     └── CondicionMedica (ManyToMany)
 └── Administrador

Paciente ──────────────────────────────────────────► Servicio
 ├── FamiliarPaciente (relacion Familiar-Paciente)
 ├── CondicionMedica (ManyToMany)
 └── RecordatorioMedicacion

Servicio
 ├── Pago
 ├── Calificacion
 ├── EvaluacionCuidado
 ├── TareaServicio
 ├── Mensaje
 └── Reclamo

Administrador
 ├── Consejo
 └── ReporteAdmin
```

---

## Instalacion y Configuracion

### Prerequisitos

- Java 17+
- Maven 3.9+
- PostgreSQL 15+
- Git

### 1. Clonar el repositorio

```bash
git clone https://github.com/Fabriziolara17/CareMe-Prueba-commits.git
cd CareMe-Prueba-commits
```

### 2. Crear la base de datos

Conectarse a PostgreSQL y ejecutar:

```sql
CREATE DATABASE careme_db;
```

### 3. Configurar variables de entorno

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/careme_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASENA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Nota:** En un entorno de produccion (AWS), estas credenciales deben configurarse como variables de entorno del sistema o mediante AWS Secrets Manager. Nunca subir credenciales reales al repositorio.

### 4. Compilar y ejecutar

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

La aplicacion estara disponible en: `http://localhost:8080`

---

## Documentacion de la API

Una vez levantada la aplicacion, la documentacion interactiva generada por **Swagger UI** esta disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

Tambien puedes acceder a la especificacion OpenAPI en formato JSON:

```
http://localhost:8080/v3/api-docs
```

---

## Endpoints Principales

| Modulo | Metodo | Endpoint | Descripcion |
|---|---|---|---|
| Auth | `POST` | `/api/auth/login` | Inicio de sesion |
| Auth | `POST` | `/api/auth/registro` | Registro de usuario |
| Auth | `POST` | `/api/auth/recuperar-password` | Solicitar recuperacion |
| Auth | `POST` | `/api/auth/reset-password` | Restablecer contrasena |
| Cuidadores | `GET` | `/api/cuidadores` | Listar cuidadores activos |
| Cuidadores | `GET` | `/api/cuidadores/{id}` | Obtener cuidador por ID |
| Servicios | `POST` | `/api/servicios` | Solicitar un servicio |
| Servicios | `GET` | `/api/servicios/{id}` | Detalle del servicio |
| Pagos | `POST` | `/api/pagos/yape` | Registrar pago por Yape |
| Pagos | `POST` | `/api/pagos/procesar` | Procesar pago generico |
| Pacientes | `GET` | `/api/pacientes` | Listar pacientes |
| Recordatorios | `POST` | `/api/recordatorios` | Crear recordatorio de medicacion |
| Calificaciones | `POST` | `/api/calificaciones` | Calificar un servicio |
| Mensajes | `POST` | `/api/mensajes` | Enviar mensaje |
| Reclamos | `POST` | `/api/reclamos` | Registrar reclamo |
| Admin | `GET` | `/api/administradores` | Panel de administracion |
| Consejos | `GET` | `/api/consejos` | Listar consejos de salud |

> La lista completa de endpoints con parametros y esquemas de respuesta esta disponible en Swagger UI.

---

## Equipo

Proyecto desarrollado como parte del curso **SI385 - Aplicaciones Web** en la **Universidad Peruana de Ciencias Aplicadas (UPC)**.

| Nombre | Codigo |
|---|---|
| Luis Andres Arce Huaman | U20201a300 |
| Mattias Adrian Concha Ochoa | U202318269 |
| Pedro Antero Figueroa Chacon | U20191e516 |
| Carlos Fabrizio Lara Talla | U202114534 |
| Renzo Fabrizio Villanueva Ramirez | U202210239 |
| Luis Mauricio Zavalaga Rios | U20221e978 |

**Startup:** MediTec  
**Producto:** CareMe  
**Universidad:** Universidad Peruana de Ciencias Aplicadas — UPC

---

<div align="center">

*MediTec © 2025 — Todos los derechos reservados*

</div>
