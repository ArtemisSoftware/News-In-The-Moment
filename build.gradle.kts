import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.20"
}

group = "com.artemissoftware"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation("org.jetbrains.compose.material3:material3-desktop:1.5.3")

                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")

                implementation("com.google.code.gson:gson:2.8.9")

                implementation("io.ktor:ktor-client-cio-jvm:2.3.2")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.2")

                implementation("io.insert-koin:koin-core:3.2.0")
                implementation("io.insert-koin:koin-logger-slf4j:3.2.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NewsInTheMoment"
            packageVersion = "1.0.0"
        }
    }
}
