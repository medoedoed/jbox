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

