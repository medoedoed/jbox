plugins {
    id("java")
    id("org.graalvm.buildtools.native") version "0.10.6"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("JBoxApplication")
}

graalvmNative {
    binaries {
        named("main") {
            resources.autodetect()
            imageName.set("jbox")
            mainClass.set("JBoxApplication")
            buildArgs.add("--no-fallback")
        }
    }
}

tasks.named("nativeCompile") {
    finalizedBy("installCliBinary")
}

val isWindows = System.getProperty("os.name").contains("win")

tasks.register("installCliBinary") {
    dependsOn("nativeCompile")
    doLast {
        val outputBinary = file("build/native/nativeCompile/jbox") // от graalvm
        val target = if (isWindows)
            File(System.getenv("ProgramFiles") ?: "C:\\Program Files", "jbox.exe")
        else
            File("/usr/local/bin/jbox")

        println("Copying CLI binary to $target (may need sudo)")
        if (!isWindows) {
            exec {
                commandLine("sudo", "cp", outputBinary.absolutePath, target.absolutePath)
            }
        } else {
            outputBinary.copyTo(target, overwrite = true)
        }
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

dependencies {
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")

    implementation("info.picocli:picocli:$picoliVersion")
    implementation("info.picocli:picocli-codegen:$picoliVersion")

    implementation("com.typesafe:config:1.4.2")

    implementation(project(":grpc"))
}

repositories {
    mavenCentral()
}

