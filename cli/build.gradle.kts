plugins {
    id("java")
    id("org.graalvm.buildtools.native") version "0.10.6"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("cli.JBoxApplication")
}

graalvmNative {
    binaries {
        named("main") {
            resources.autodetect()
            imageName.set("jbox")
            mainClass.set("cli.JBoxApplication")
            buildArgs.add("--no-fallback")
            buildArgs.add("--initialize-at-build-time=kotlin.DeprecationLevel")
            buildArgs.add("--initialize-at-build-time=org.slf4j")
        }
    }
}

tasks.named("nativeCompile") {
    finalizedBy("installCliBinary")
}

val isWindows = System.getProperty("os.name").contains("win", ignoreCase = true)

val installDir = if (isWindows) {
    File(System.getenv("USERPROFILE"), "AppData/Local/jbox/bin")
} else {
    File(System.getProperty("user.home"), ".local/bin")
}

tasks.register("installCliBinary") {
    dependsOn("nativeCompile")
    doLast {
        val outputBinary = file("build/native/nativeCompile/jbox" + if (isWindows) ".exe" else "")
        val target = File(installDir, if (isWindows) "jbox.exe" else "jbox")

        installDir.mkdirs()
        outputBinary.copyTo(target, overwrite = true)
        target.setExecutable(true)

        println("Copied CLI binary to: ${target.absolutePath}")
        println("Make sure ${installDir.absolutePath} is in your PATH")
    }
}


tasks {
    shadowJar {
        archiveFileName.set("jbox.jar")
        mergeServiceFiles()
    }
}


group = "ru.medo"


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

val grpcVersion = "1.70.0"
val picoliVersion = "4.7.6"
val daggerVersion = "2.52"

dependencies {
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")

    implementation("com.google.inject:guice:7.0.0")

    implementation("info.picocli:picocli:$picoliVersion")
    implementation("info.picocli:picocli-codegen:$picoliVersion")

    implementation("ch.qos.logback:logback-classic:1.5.16")

    implementation("com.typesafe:config:1.4.2")
    implementation("org.zeroturnaround:zt-exec:1.12")

    implementation(project(":grpc"))
    implementation(project(":config"))
}

repositories {
    mavenCentral()
}

