plugins {
    kotlin("jvm") version "1.9.22"
    id("me.champeau.jmh") version "0.7.0"
    id("org.jetbrains.dokka") version "1.8.10"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    implementation("org.reflections:reflections:0.10.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

jmh {
    threads = 1
    fork = 1
    warmupIterations = 1
    iterations = 1
}
