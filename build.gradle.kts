plugins {
    kotlin("jvm") version "1.9.22"
    id("me.champeau.jmh") version "0.7.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
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