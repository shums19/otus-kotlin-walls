plugins {
    kotlin("jvm")
}

allprojects {
    group = "ru.otus.kotlin.walls"
    version = "1.0-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
