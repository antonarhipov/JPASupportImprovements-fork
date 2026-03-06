# Kotlin JPA allOpen Demo

Demonstrates why `allOpen` is required for JPA entities in Kotlin.

## The Problem

Kotlin classes are `final` by default. Hibernate needs to create proxy subclasses for lazy loading. **You can't subclass a final class.**

Without `allOpen`, your `@ManyToOne(fetch = FetchType.LAZY)` silently becomes eager:

```
// Loading a Book ALSO loads Author immediately
SELECT from books WHERE id=?
SELECT from authors WHERE id=?  ← Eager! Before you even access author
```

With `allOpen`:

```
// Loading a Book does NOT load Author
SELECT from books WHERE id=?

// Only when you access author.name
SELECT from authors WHERE id=?  ← Lazy! Only when needed
```

## The Fix

```kotlin
// build.gradle.kts
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
```

Or use the `kotlin-jpa` plugin which handles this automatically.

## Running Tests

```bash
# Tests pass with allOpen enabled
./gradlew test

# To see failures, comment out allOpen block in build.gradle.kts
```

## Stack

- Kotlin 2.3.0
- Spring Boot 4.1.0-M1
- Hibernate 7.2.1.Final
