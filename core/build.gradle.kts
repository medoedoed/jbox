plugins {
    id("java")
    kotlin("jvm") version "2.1.20"
    id("org.graalvm.buildtools.native") version "0.9.28"
    id("application")
    kotlin("plugin.serialization") version "2.1.0"
}

application {
    mainClass.set("core.CoreAppKt")
}

val binaryName = "core"

graalvmNative {
    binaries {
        named("main") {
            resources.autodetect()
            imageName.set(binaryName)
            mainClass.set("core.CoreAppKt")
            buildArgs.add("--no-fallback")
            buildArgs.add("--initialize-at-build-time=org.slf4j")
            buildArgs.add("--initialize-at-build-time=ch.qos.logback")
            buildArgs.add("--initialize-at-build-time=kotlin.DeprecationLevel")
//            buildArgs.add("--trace-class-initialization=ch.qos.logback")
//            buildArgs.add("--trace-class-initialization=org.slf4j.LoggerFactory")
        }
    }
}

tasks.named("nativeCompile") {
    finalizedBy("installCoreBinary")
}

tasks.register("installCoreBinary") {
    dependsOn("nativeCompile")
    doLast {
        val outputBinary = file("build/native/nativeCompile/$binaryName")
        val targetBinary = File(System.getProperty("user.home"), ".jbox/bin/$binaryName")
        println("Copying core binary to $targetBinary")
        targetBinary.parentFile.mkdirs()
        outputBinary.copyTo(targetBinary, overwrite = true)
        targetBinary.setExecutable(true)
    }
}



group = "ru.medo"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

val grpcVersion = "1.70.0"
val grpcKotlinVersion = "1.4.1"
val exposedVersion = "0.61.0"
val kotlinVersion = "2.1.20"

dependencies {
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-services:$grpcVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
//    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
//    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("io.insert-koin:koin-core:4.0.4")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("ch.qos.logback:logback-core:1.5.16")

    implementation(project(":config"))
    implementation(project(":grpc"))
//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.test {
    useJUnitPlatform()
}
