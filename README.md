# JBox — CLI wrapper for sing-box


JBox is a native command-line wrapper for [sing-box](https://github.com/SagerNet/sing-box), providing a minimalistic interface, configuration management, and a gRPC-controlled daemon.


## Overview

- Fully native binary; no JVM or runtime dependencies.
- Separation of concerns via modular design: CLI frontend + gRPC-managed core daemon.
- Embedded SQLite database for profile and state management.
- Cross-platform support (Linux x86_64, Windows amd64).
## Technology Stack

| Component            | Technology                                                     |
|----------------------|----------------------------------------------------------------|
| Language             | Kotlin, Java                                                   |
| CLI Framework        | [Picocli](https://picocli.info)                                |
| Native Compilation   | [GraalVM Native Image](https://www.graalvm.org)                |
| Database             | SQLite via [Exposed ORM](https://github.com/JetBrains/Exposed) |
| gRPC                 | Java gRPC + Kotlin Stubs                                       |
| Dependency Injection | Koin (Kotlin), Guice (Java)                                    |
| Configuration        | Typesafe Config                                                |


## Build from source

Requirements:
- GraalVM with `native-image` installed (Java 17+)
- `JAVA_HOME` set to GraalVM
- On Linux: `build-essential`, `zlib1g-dev`, `libffi-dev` installed

```shell
./gradlew build
```