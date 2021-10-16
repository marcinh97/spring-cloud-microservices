import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    id("com.google.cloud.tools.jib") version "3.1.4"
}

group = "com.horakm"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-client
    implementation("org.springframework.cloud:spring-cloud-config-client:3.0.5")
// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.4")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.0.4")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.0.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
// enables actuator/info
springBoot {
    buildInfo()
}

jib {
    to {
        image = "horakmarcin/main:sample-service"
        credHelper = "osxkeychain"
    }
}
