import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
	id("com.google.cloud.tools.jib") version "3.1.4"
}

group = "com.horakm"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
var dockerImgName = "example-service"

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// enables actuator (health info etc.)
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// sets up swagger - available at xyz/swager-ui.html:
	implementation("org.springdoc:springdoc-openapi-webmvc-core:1.5.11")
	implementation("org.springdoc:springdoc-openapi-ui:1.5.11")

	// sets up prometheus - available at xyz/actuator/prometheus
	implementation("io.micrometer:micrometer-registry-prometheus:latest.release") // prometheus

	// sets up logstash (which will enable to send logs to Elastic search)
	implementation("net.logstash.logback:logstash-logback-encoder:6.6")

	// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-client
	implementation("org.springframework.cloud:spring-cloud-config-client:3.0.5")
// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.4")

    // eureka client - for service discovery
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.0.4")

    runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
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

springBoot {
	buildInfo()
}

jib {
	to {
		image = "horakmarcin/main:example-service"
		credHelper = "osxkeychain"
	}
}
