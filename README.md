# RideMove — Backend (Primera Review Técnica)

Backend en **Spring Boot 3 + Java 21**, con **Arquitectura Hexagonal (puertos y adaptadores)**,
**JWT**, **PostgreSQL** y configuración 100% por **variables de entorno** (nada quemado en código).

## 1. Estructura (Arquitectura Hexagonal)

```
com.ridemove
├── domain                     # Núcleo del negocio, sin dependencias de frameworks
│   ├── model                  # Usuario, Rol
│   ├── exception
│   └── port
│       ├── in                 # Casos de uso que expone el dominio (interfaces)
│       └── out                # Contratos que el dominio necesita (repo, JWT, hash)
├── application
│   └── usecase                # Implementación de los casos de uso (RegistrarUsuarioService, etc.)
└── infrastructure
    ├── adapter
    │   ├── in.web              # Controllers REST + DTOs (traducen HTTP -> caso de uso)
    │   └── out.persistence     # JPA/PostgreSQL implementando UsuarioRepositoryPort
    │   └── out.security        # BCrypt y JWT implementando los puertos de seguridad
    └── config                  # SecurityConfig, filtro JWT, propiedades
```

**Regla de dependencia:** `domain` no importa nada de Spring/JPA/JWT. `application` solo depende de
`domain`. `infrastructure` es la única capa que conoce frameworks concretos. Esto es justo lo que
pide la guía en "Mantenibilidad" y en la sección de backend (Controller → Service/UseCase → Repository).

## 2. Principios SOLID aplicados

- **SRP**: cada caso de uso (`RegistrarUsuarioService`, `AutenticarUsuarioService`, `DashboardService`)
  tiene una sola razón para cambiar.
- **DIP**: los casos de uso dependen de interfaces (`UsuarioRepositoryPort`, `PasswordEncoderPort`,
  `TokenProviderPort`), no de JPA ni de jjwt directamente.
- **ISP**: los puertos de salida están separados por responsabilidad (repositorio, cifrado, token)
  en vez de una interfaz gigante.
- **OCP**: se puede añadir un nuevo adaptador (p. ej. otro proveedor de token) sin tocar los casos de uso.

## 3. Los 12 factores aplicados aquí

| Factor | Cómo se cumple |
|---|---|
| Config | Todo en `application.yml` vía `${VARIABLE}`, ver `.env.example` |
| Backing services | PostgreSQL es un recurso externo (URL por variable de entorno) |
| Procesos sin estado | JWT stateless, `SessionCreationPolicy.STATELESS`, sin sesión en servidor |
| Logs | Salen por stdout (config por defecto de Spring Boot), nivel configurable por env |
| Port binding | `SERVER_PORT` configurable |
| Paridad dev/prod | Mismos artefactos, solo cambian variables de entorno por perfil |

## 4. Variables de entorno

Copia `.env.example` y ajusta los valores. En IntelliJ: *Run/Debug Configurations → Environment
variables*. **Nunca subas `.env` a git** (ya está en `.gitignore`).

## 5. Cómo correr

```bash
mvn clean install
mvn spring-boot:run
```

Necesitas una base PostgreSQL corriendo (local, contenedor o remota) y las variables `DB_URL`,
`DB_USER`, `DB_PASSWORD` apuntando a ella. Con `JPA_DDL_AUTO=update` Hibernate crea la tabla
`usuario` automáticamente en el primer arranque.

## 6. Las 3 funcionalidades para la review

| # | Funcionalidad | Endpoint | Seguridad |
|---|---|---|---|
| 1 | Registro | `POST /api/auth/registro` | Público |
| 2 | Login | `POST /api/auth/login` | Público, devuelve JWT |
| 3 | Dashboard | `GET /api/dashboard` | Requiere `Authorization: Bearer <token>` |

Importa `postman/RideMove.postman_collection.json` en Postman: el login guarda el token
automáticamente y el dashboard lo reutiliza — ideal para la demo en vivo (sección 1.8/2.1/2.2 de la
guía: request, response, código HTTP y flujo completo).

Flujo a mostrar en la review, tal como pide la guía:
`Usuario → Frontend → API (Backend) → Base de Datos`, validando después en la base de datos que el
usuario quedó realmente guardado y que la contraseña **no** aparece en texto plano (BCrypt).

## 7. Estrategia de ramas de Git

```
main             → versión estable ya mostrada en comité
pre-produccion   → integración antes de pasar a producción (QA)
produccion       → lo desplegado / lo que se demuestra en la review
```

```bash
git init
git checkout -b main
git add .
git commit -m "chore: estructura inicial hexagonal RideMove backend"
git checkout -b pre-produccion
git checkout -b produccion
```

Trabajen con ramas de feature (`feature/registro`, `feature/login`, `feature/dashboard`) que se
mergean primero a `pre-produccion` y de ahí a `produccion`, manteniendo `main` como referencia
estable del proyecto.

## 8. Pendiente para completar el checklist de la guía

- Diagrama de arquitectura (Usuario → Frontend → API → Base de Datos) para la presentación.
- Backlog en Jira con las 9 RF del documento, priorizado (ya viene priorizado en el documento entregado).
- Mockups en Figma de las pantallas de Registro, Login y Dashboard.
- Frontend/app móvil que consuma estos 3 endpoints (pendiente de definir framework — ver conversación).
