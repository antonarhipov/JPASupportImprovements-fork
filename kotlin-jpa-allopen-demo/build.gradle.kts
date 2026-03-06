plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.3.10"
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "7.2.1.Final"
    id("org.graalvm.buildtools.native") version "0.11.4"
    kotlin("plugin.jpa") version "2.3.10"
}

group = "com.jetbrains.demo"
version = "0.0.1-SNAPSHOT"
description = "JPA-allopen-demo"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-h2console")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

// ============================================================================
// EXPERIMENT FINDINGS - JPA/Hibernate with Kotlin and allOpen
// ============================================================================
//
// SCENARIO 1: WITHOUT allOpen, WITHOUT bytecode enhancement
// Result: Lazy loading BROKEN - @ManyToOne loaded EAGERLY (extra SELECT immediately)
// Reason: Hibernate can't create proxy subclass for final Kotlin class
//
// SCENARIO 2: WITHOUT allOpen, WITH bytecode enhancement (enableAssociationManagement)
// Result: Lazy loading COMPLETELY BROKEN - field access returns null!
// Reason: Fields marked lazy but property access can't be intercepted on final class
//
// SCENARIO 3: WITH allOpen, WITHOUT bytecode enhancement
// Result: Lazy loading WORKS - proper HibernateProxy created
// Reason: Hibernate can create proxy subclass for open class
//
// CONCLUSION: You NEED allOpen for JPA entities in Kotlin for lazy loading to work!
// ============================================================================

// DISABLED: Hibernate bytecode enhancement causes issues with final classes
// hibernate {
//     enhancement {
//         enableAssociationManagement = true
//     }
// }

// REQUIRED: Enable allOpen for JPA entities for lazy loading to work properly
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
