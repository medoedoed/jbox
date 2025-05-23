plugins {
    id("java")
    kotlin("jvm") version "2.1.20"
    id("org.graalvm.buildtools.native") version "0.9.28"
    id("application")

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
val exposedVersion = "0.60.0"
val kotlinVersion = "2.1.20"

dependencies {
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-services:$grpcVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
//    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("io.insert-koin:koin-core:4.0.4")

    implementation("com.typesafe:config:1.4.2")
//    implementation("io.github.config4k:config4k:0.7.0")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.9")


    implementation(project(":grpc"))
//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.test {
    useJUnitPlatform()
}
