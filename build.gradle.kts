plugins {
    java
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

group = "de.dreadlabs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin {
    sourceSets {
        test {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:[5.0,6.0)")
                implementation(kotlin("test-junit5"))

                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:[5.0,6.0)")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
