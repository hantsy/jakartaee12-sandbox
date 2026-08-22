# jakartaee12-sandbox

Playground of new features in Jakarta EE 12.

A multi-module Maven project exploring the Jakarta EE 12 milestone specs, running
on GlassFish 9 and Java 25.

## Modules

| Module       | Packaging | Spec / API                                             | Example                                        |
|--------------|-----------|--------------------------------------------------------|------------------------------------------------|
| `faces`      | war       | Jakarta Faces 5.0.0-M5                                 | `hello.xhtml` (placeholder attr, EnumConverter, SUCCESS severity) |
| `servlet`    | war       | Jakarta Servlet 6.2.0-M2                               | `HelloServlet` (`@WebServlet("/hello")`)       |
| `persistence`| war       | Jakarta Persistence 4.0.0-M6                           | `Person` entity + `PersonRepository`           |
| `cdi`        | war       | Jakarta CDI 5.0.0.CR1                                  | `GreetingService` injected into a servlet      |
| `security`   | war       | Jakarta Security 5.0.0-M2 + GlassFish MP-JWT           | form + JWT auth via `HttpAuthenticationMechanismHandler` |
| `hibernate`  | jar       | Hibernate ORM 8.0.0.Beta1 (JPA 4.0) + H2               | `Book`/`Author` mapping + unit test            |

## Requirements

- JDK 25
- Maven 4 (4.0.0-rc-x)

## Build

```bash
# Compile everything and run unit tests (hibernate module)
mvn clean install

# Run only the Hibernate unit test
mvn -pl hibernate test

# Run Arquillian integration tests against GlassFish 9 (downloads GlassFish 9.0.0-M3)
mvn -pl faces,servlet,persistence,cdi clean verify -Parq-glassfish-managed

# Manual deploy to GlassFish 9 via Cargo (starts the server)
mvn -pl faces cargo:run -Pglassfish
```

## Version notes

- Jakarta EE 12 has no published platform BOM yet, so each spec artifact is
  pinned at its latest milestone in the root `pom.xml`.
- Hibernate ORM 8.0.0.Beta1 implements Jakarta Persistence 4.0 (the 7.x line
  targets Jakarta Persistence 3.2 / EE 11).
- GlassFish 9.0.0-M3 is deployed via the Cargo Maven plugin (`glassfish9x`) and
  tested with the Arquillian managed container (hantsy adapter).
- Jakarta CDI 5.0 relocated `jakarta.enterprise:jakarta.enterprise.cdi-api` to
  `jakarta.cdi:jakarta.cdi-api` (used here).
- The `security` module dispatches between a web form mechanism
  (`@CustomFormAuthenticationMechanismDefinition`) and GlassFish's MicroProfile
  JWT mechanism (`@MicroProfileJwtAuthenticationMechanism` from
  `org.glassfish.main.common:glassfish-api`, provided scope) via a custom
  `HttpAuthenticationMechanismHandler`.
- Descriptors declare the Jakarta EE 12 schema versions (`web-app_6_2`,
  `web-facesconfig_5_0`, `beans_5_0`, `persistence_4_0`). Note: GlassFish
  9.0.0-M3's schema repository (`lib/schemas`) does not ship these yet, so the
  Arquillian/Cargo deployment on GlassFish currently fails until a later
  GlassFish milestone adds them.
