plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":ok-walls-common"))
    implementation(project(":ok-walls-stubs"))
}
