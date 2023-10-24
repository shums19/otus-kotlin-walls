plugins {
    kotlin("jvm")
}

dependencies {
    val cache4kVersion: String by project
    val kmpUUIDVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":ok-walls-common"))

    implementation(kotlin("stdlib"))
    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    testImplementation(project(":ok-walls-repo-tests"))
    implementation(kotlin("test-junit"))
}
