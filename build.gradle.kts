plugins {
    id("java")
//    id("io.quarkus") version "3.23.0.CR1"

}

group = "ru.medo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

//tasks.quarkusDev {
//    workingDirectory = rootProject.layout.projectDirectory.asFile
//}

tasks.test {
    useJUnitPlatform()
}

