plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "jbox"

include("core")
include("cli")
include("gui")
include("grpc")
include("config")
