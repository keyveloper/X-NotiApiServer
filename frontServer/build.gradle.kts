plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"

    // for query DSL
    kotlin("kapt") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"


// query DSL setting - Q class
kapt{
    includeCompileClasspath = false
    arguments {
        arg("querydsl.entityAccessors", "true")
        arg("querydsl.useKotlinClass", "true")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    // query DSL
    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt ("jakarta.annotation:jakarta.annotation-api")
    kapt ("jakarta.persistence:jakarta.persistence-api")

    // jwt & security
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // validation
    implementation("org.passay:passay:1.6.1")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Logger
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")

    // webClient
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Circuit Breaker : Resilience4j
    implementation("org.springframework.boot:spring-boot-starter-aop:3.3.4")
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.3.4")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
//