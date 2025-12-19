plugins {
	kotlin("jvm") version "2.3.0"
	kotlin("plugin.spring") version "2.3.0"
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.gitlab.arturbosch.detekt") version "1.23.8"
	id("com.google.cloud.tools.jib") version "3.5.2"
	id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "de.codecentric"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.oshai:kotlin-logging-jvm:7.0.13")
	implementation("net.logstash.logback:logstash-logback-encoder:9.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("io.kotest:kotest-assertions-core-jvm:6.0.7")
	testImplementation("io.mockk:mockk:1.14.7")
	testImplementation("com.ninja-squad:springmockk:5.0.1")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.1")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot {
	mainClass.set("de.codecentric.heather.HeatherApplicationKt")
}

detekt {
	autoCorrect = true
	config.setFrom("$projectDir/config/detekt/detekt.yml")
	buildUponDefaultConfig = true
}

dependencies {
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

// avoids failures like "detekt was compiled with Kotlin 2.0.21 but is currently running with 2.2.10."
configurations.matching { it.name == "detekt" }.all {
	resolutionStrategy.eachDependency {
		if (requested.group == "org.jetbrains.kotlin") {
			@Suppress("UnstableApiUsage")
			useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
		}
	}
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektWithIgnoreFailures") {
	description = "Run detekt with auto-correct, ignoring failures"
	group = "verification"
	ignoreFailures = true
	autoCorrect = true
	config.setFrom("$projectDir/config/detekt/detekt.yml")
	buildUponDefaultConfig = true
	setSource(files("src/main/kotlin", "src/test/kotlin"))
}

tasks.register("detektTwice") {
	description = "Run detekt twice - first with auto-correct, second to verify"
	group = "verification"
	// This causes the tasks to run in serial. If both tasks are in dependsOn, they run in parallel.
	dependsOn("detektWithIgnoreFailures")
	finalizedBy("detekt")
}

jib {
	val gcpProject = System.getenv("GCP_PROJECT") ?: "myproj"
	val artifactRepo = System.getenv("ARTIFACT_REGISTRY_REPO") ?: "heather"
	val appName = System.getenv("BACKEND_APP_NAME") ?: "heather-backend"
	val imageTag = System.getenv("CI_COMMIT_SHA") ?: project.version.toString()
	
	to {
		image = "europe-west1-docker.pkg.dev/$gcpProject/$artifactRepo/$appName:$imageTag"
	}
	
	from {
		image = "eclipse-temurin:21-jre"
	}
	
	container {
		mainClass = "de.codecentric.heather.HeatherApplicationKt"
		jvmFlags = listOf(
			"-Xmx400m",
			"-XX:MaxMetaspaceSize=128m",
			"-XX:ReservedCodeCacheSize=64m"
		)
		ports = listOf("8080")
		environment = mapOf(
			"SPRING_PROFILES_ACTIVE" to "cloud"
		)
	}
}

openApi {
	apiDocsUrl.set("http://localhost:8080/v3/api-docs")
	outputDir.set(file("$projectDir/../frontend/src/api/generated"))
	outputFileName.set("openapi.json")
	waitTimeInSeconds.set(30)
}

