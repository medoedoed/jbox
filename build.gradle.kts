import java.io.*

plugins {
    id("java")
}

group = "ru.medo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

val jBoxHomeBin = File(System.getProperty("user.home"), ".jbox/bin")
jBoxHomeBin.mkdirs()
val os = if (System.getProperty("os.name").lowercase().contains("win")) "windows" else "linux"

tasks.register<Copy>("installSingBox") {
    from(file("bin/$os"))
    into(jBoxHomeBin)
    doLast {
        val binary = if (os == "windows") File(jBoxHomeBin, "sing-box.exe") else File(jBoxHomeBin, "sing-box")
        binary.setExecutable(true)
        println("sing-box installed to ${binary.absolutePath}")
    }
}


tasks.named("build") {
    dependsOn("installSingBox")
     dependsOn(":core:nativeCompile", ":cli:nativeCompile") // закомментируй, если этих задач ещё нет
}