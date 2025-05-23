import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
    id("java")
}

group = "ru.medo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

val jboxHomeBin = File(System.getProperty("user.home"), ".jbox/bin")
val os = if (System.getProperty("os.name").contains("win")) "windows" else "linux"

val arch = when {
    System.getProperty("os.arch").contains("64") -> "amd64"
    System.getProperty("os.arch").contains("aarch64") -> "arm64"
    else -> throw GradleException("Unsupported architecture")
}

val singBoxVersion = "1.11.11"
val singBoxUrl = "https://github.com/SagerNet/sing-box/releases/download/v$singBoxVersion/sing-box-$singBoxVersion-$os-$arch.tar.gz"

val singBoxPath = jboxHomeBin.resolve("sing-box")
val singBoxTar = jboxHomeBin.resolve("sing-box.tar.gz")

tasks.register("downloadSingBox") {
    outputs.file(singBoxPath)
    doLast {
        if (!jboxHomeBin.exists()) jboxHomeBin.mkdirs()
        if (!singBoxPath.exists()) {
            println("Downloading sing-box from $singBoxUrl")
            URL(singBoxUrl).openStream().use { input ->
                Files.copy(input, singBoxTar.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }
            println("Extracting...")
            exec {
                workingDir = jboxHomeBin
                commandLine("tar", "-xzf", singBoxTar.name)
            }

            val extracted = jboxHomeBin.listFiles()?.firstOrNull { it.name.startsWith("sing-box") && it.canExecute() }
                ?: throw GradleException("sing-box binary not found after extraction")
            extracted.renameTo(singBoxPath)
            singBoxPath.setExecutable(true)
            singBoxTar.delete()
        } else {
            println("sing-box already exists at $singBoxPath")
        }
    }
}

tasks.named("build") {
    dependsOn(":downloadSingBox", ":core:nativeCompile", ":cli:nativeCompile")
}

tasks.test {
    useJUnitPlatform()
}

