# FormulaForecast – Copilot Instructions

## Build, Test & Run

```bash
# Build (skip tests)
./gradlew build -x test

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.steve.formulaforecast.api.model.raceweekend.ActiveRaceWeekendUpdateServiceTest"

# Run locally (demo profile, uses hardcoded localhost DB creds)
java -Dspring.profiles.active=demo -Xms512m -Xmx1024m -jar build/libs/formulaforecast-0.0.1-SNAPSHOT.jar

# Docker (requires .env with ENV, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD, JWT_SECRET_KEY)
docker-compose up
```

Spring Boot 4.0.0-M2, Java 21, PostgreSQL 16, Flyway migrations.

## Architecture

Three-layer structure strictly separated by package:

```
api/          → REST controllers + request/response DTOs
service/      → business logic + domain model objects
persistence/  → Spring Data JDBC repositories + entity classes
job/          → Quartz scheduled jobs
```

The **service layer owns the domain model** — domain objects live in `service/*/model/` or directly in the service package. API DTOs in `api/*/model/` are mapped at the controller boundary and never leak into the service layer.

**Persistence** uses Spring Data JDBC (not JPA). All repositories extend `Repository<Entity, Long>` and use explicit `@Query` SQL strings — no derived query methods or `@Entity` annotations.

**Auth flow:** Stateless JWT stored in cookies. `JwtAuthenticationFilter` validates the token and sets the `SecurityContext`. `/api/v1/auth/**` is public; everything else requires authentication. The currently authenticated user is resolved via `AuthenticatedAccountProvider`.

**Scheduled jobs** use Quartz (in-memory job store). Jobs implement `org.quartz.Job`, are annotated with `@JobIdentifier` and `@JobDescription`, and delegate to a service. `ActiveRaceWeekendScheduledJob` runs every minute to advance race weekend state (UPCOMING → RACE_WEEK → LIVE → COMPLETE).

**Database IDs:** Tables use a Long surrogate `id` as the internal primary key and a UUID `*_uid` column as the stable external identifier. Only UIDs are exposed via the API.

## Key Conventions

**Naming:**
- REST controllers → `*Resource` (e.g., `PredictionsResource`)
- Business-logic services → `*Service` (e.g., `PredictionService`)
- DB-access services wrapping repositories → `*PersistenceService` (e.g., `PredictionPersistenceService`)
- Spring Data JDBC interfaces → `*Repository`
- DB-mapped objects → `*Entity`
- API response wrappers → `*Response`, field-level DTOs → `*Dto`

**Profiles:**
| Profile | DB config |
|---------|-----------|
| *(default / no profile)* | Hardcoded `localhost:5432` creds |
| `dev` | Env vars: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` |
| `demo` | Same as default – used for local demos |
| `prod` | Env vars (same as dev) |

CORS allowed origins are configured in `cors.allowed-origins` in the profile yaml and injected via `CorsProperties`.

**Time handling:** `InstantSource` is injected (not `Clock` or `Instant.now()`) so tests can supply a fixed instant via `@TestConfiguration`. All date comparisons use London timezone (`TimeZones.LONDON_TIME`).

**Tests:** Integration tests use `@SpringBootTest` with `@MockitoBean`. The test datasource is H2 in PostgreSQL-compatibility mode (`MODE=PostgreSQL`). Flyway runs migrations against H2 on test startup. Quartz auto-startup is disabled in tests (`quartz.auto-startup: false`).

**Flyway migrations** live in `src/main/resources/db/migration/` and follow the `V{n}__description.sql` naming convention. Always add a new versioned migration rather than editing existing ones.
