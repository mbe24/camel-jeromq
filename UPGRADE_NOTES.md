# Tech Stack Upgrade Notes

## Overview
This document describes the tech stack upgrade from legacy versions to the latest stable versions as of February 2026.

## Version Changes

| Component | Old Version | New Version |
|-----------|-------------|-------------|
| Java | 8 (1.8) | 25 |
| Gradle | 5.3 | 9.3.1 |
| Spring Boot | 2.1.2.RELEASE | 3.5.9 |
| Apache Camel | 2.23.1 | 4.17.0 |
| JeroMQ | 0.4.0 | 0.6.0 |
| JUnit | 4.12 | 5.x (Jupiter) |
| Spring Dependency Management | 1.0.6.RELEASE | 1.1.7 |

## Breaking Changes

### 1. Java Version
- **Change**: Java 8 → Java 25
- **Impact**: Uses latest LTS features and syntax
- **Configuration**: Uses Gradle Java toolchain to automatically detect Java 25
- **Setup**: Set `JAVA_HOME` environment variable to Java 25 installation or let Gradle auto-detect

### 2. Gradle
- **Change**: Gradle 5.3 → Gradle 9.3.1
- **Impact**: 
  - Deprecated `mainClassName` replaced with `mainClass`
  - Build scan plugin removed (deprecated)
  - Better Kotlin DSL support

### 3. Spring Boot
- **Change**: Spring Boot 2.1.2 → Spring Boot 3.5.9
- **Impact**:
  - Requires Java 17+ (we use Java 25)
  - Uses Jakarta EE instead of javax
  - Configuration unchanged for this project (already compatible)

### 4. Apache Camel
- **Change**: Apache Camel 2.23.1 → 4.17.0
- **Impact**:
  - Major API changes from Camel 2.x to 4.x
  - `org.apache.camel.impl.DefaultShutdownStrategy` removed
  - Shutdown strategy now configured via application.yml (already present)
  - Package restructuring (some classes moved)

### 5. JUnit
- **Change**: JUnit 4 → JUnit 5 (Jupiter)
- **Impact**:
  - Changed from `@RunWith` to `@SpringBootTest`
  - Changed from `org.junit.Assert` to `org.junit.jupiter.api.Assertions`
  - Changed `@Test` import from `org.junit` to `org.junit.jupiter.api`
  - Added `useJUnitPlatform()` to test configuration

### 6. Repository
- **Change**: jcenter() → mavenCentral()
- **Impact**: jcenter() is deprecated and read-only since 2021

## Known Issues

### ZeroMQ Component Incompatibility

**Problem**: The `camel-zeromq` component from Apache Camel Extras (camel-extra) is not compatible with Apache Camel 4.x. The latest version available (2.25.2) only supports Camel 2.x and early 3.x.

**Error**: `NoClassDefFoundError: org/apache/camel/impl/DefaultComponent`

**Current Solution**: The ZeroMQ routes have been temporarily modified to use alternative Camel endpoints:
- Publisher route (`zmq-pub`): Uses `timer` → `log` instead of `timer` → `zeromq`
- Subscriber route (`zmq-sub`): Uses `direct` endpoint instead of `zeromq`

**Future Solutions**:
1. **Wait for camel-extra update**: Monitor https://github.com/apache/camel-extra for Camel 4.x support
2. **Custom component**: Implement a custom Camel component for JeroMQ that's compatible with Camel 4.x
3. **Alternative messaging**: Consider using other messaging components that are Camel 4.x compatible:
   - `camel-jms`
   - `camel-kafka`
   - `camel-rabbitmq`
   - `camel-mqtt`

**Dependency Status**:
```kotlin
// Commented out until Camel 4.x compatible version is available
// implementation("org.apache-extras.camel-extra:camel-zeromq:2.25.2")
```

## Testing

### Test Coverage
All tests have been migrated to JUnit 5 and are passing:

1. **testContextLoads**: Verifies Spring Boot and Camel context loads successfully
2. **testZmqPubRouteExists**: Confirms publisher route is registered
3. **testZmqSubRouteExists**: Confirms subscriber route is registered
4. **testTimerRoute**: Validates timer route fires correctly
5. **testDirectRoute**: Tests message processing through direct route

### Running Tests
```bash
./gradlew test
```

### Building
```bash
./gradlew build
```

## Migration Guide for Developers

If you need to add new features or restore ZeroMQ functionality:

### 1. Java 25 Features
You can now use modern Java features including:
- Records
- Pattern matching
- Sealed classes
- Text blocks
- Enhanced switch expressions

### 2. Spring Boot 3.x
- Use `jakarta.*` packages instead of `javax.*`
- Update any Spring Security configurations for new API
- Review Spring Boot 3.x migration guide

### 3. Camel 4.x
- Use new package structure: `org.apache.camel.support.*`
- Shutdown strategies configured via properties
- Component implementations have moved packages
- Review Camel 4.x migration guide

### 4. JUnit 5
```java
// Old (JUnit 4)
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

@RunWith(CamelSpringBootRunner.class)
public class MyTest {
    @Test
    public void test() {
        Assert.assertTrue(condition);
    }
}

// New (JUnit 5)
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MyTest {
    @Test
    public void test() {
        assertTrue(condition);
    }
}
```

## CI/CD Updates

### Travis CI
Updated `.travis.yml` to use:
- `openjdk25` instead of `oraclejdk8`
- Removed `--scan` flag (build scan plugin removed)

## Files Modified

1. `gradle/wrapper/gradle-wrapper.properties` - Gradle version
2. `build.gradle.kts` - All dependency versions, plugins, Java version, toolchain
3. `src/main/java/org/beyene/camel/Application.java` - Removed deprecated ShutdownStrategy
4. `src/main/java/org/beyene/camel/ZmqPubRoute.java` - Modified for compatibility
5. `src/main/java/org/beyene/camel/ZmqSubRoute.java` - Modified for compatibility
6. `src/test/java/org/beyene/camel/ApplicationTest.java` - JUnit 5 migration
7. `.travis.yml` - Java version update
8. `UPGRADE_NOTES.md` - Comprehensive upgrade documentation (new file)

## Performance Notes

- Java 25 provides improved performance over Java 8
- Gradle 9.x has better incremental compilation
- Spring Boot 3.x has better startup time and memory usage
- Camel 4.x has improved routing engine performance

## Security Updates

All dependencies have been updated to latest versions with security patches:
- JeroMQ: 0.4.0 → 0.6.0 (multiple CVE fixes)
- Spring Boot: 2.1.2 → 3.5.9 (numerous security updates)
- All transitive dependencies updated automatically

## References

- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Apache Camel 4.0 Migration Guide](https://camel.apache.org/manual/camel-4-migration-guide.html)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Gradle 9.x Release Notes](https://docs.gradle.org/9.3.1/release-notes.html)
