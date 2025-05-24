plugins {
    kotlin("jvm") version "2.1.0"
}

group = "ru.medo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.typesafe:config:1.4.2")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
