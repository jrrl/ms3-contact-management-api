plugins {
  id("org.springframework.boot") version "2.4.5"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  id("com.google.cloud.tools.jib") version "3.0.0"
  id("net.researchgate.release") version "2.8.1"
  id("java")
}

group = "com.example"
version = project.version
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  compileOnly("org.projectlombok:lombok")
  runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

jib {
  to {
//    image = "docker.pkg.github.com/jrrl/ms3-contact-management-api/ms3-contact-api"
    image = "gcr.io/ms3-contact-api/ms3-contact-api"
    tags = setOf("latest")
  }
}

release {
  failOnCommitNeeded = false
  failOnUnversionedFiles = false
  failOnUpdateNeeded = false
  preTagCommitMessage = "[skip ci] tag version: "
  tagCommitMessage = "[skip ci] update to new version: "
  newVersionCommitMessage = "[skip ci] new version commit: "
}