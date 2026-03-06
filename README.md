# JPA Support Improvements

A demo application showcasing Kotlin-aware JPA support improvements in IntelliJ IDEA 2026.1.

The combination of Kotlin and Jakarta Persistence is popular for server-side development, but since Jakarta Persistence was originally designed for Java, some Kotlin features – like null safety, `val`, and `data class` semantics – can behave unexpectedly in entities.

IntelliJ IDEA 2026.1 addresses these pitfalls with more Kotlin-aware JPA support:

- Autoconfiguration of Kotlin `no-arg` and `all-open` compiler plugins when adding Kotlin to an existing project
- Removal of redundant no-arg constructions and `open` modifiers
- Detection and quick-fixes for pitfalls like using data classes and `val` fields on JPA entities
