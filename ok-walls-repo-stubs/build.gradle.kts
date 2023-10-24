plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":ok-walls-common"))
    implementation(project(":ok-walls-stubs"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(kotlin("test-junit"))
    implementation(project(":ok-walls-repo-tests"))
}
