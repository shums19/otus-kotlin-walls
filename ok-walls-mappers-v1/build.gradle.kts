plugins {
    kotlin("jvm")
}

dependencies {
    val kotestVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":ok-walls-api-v1-jackson"))
    implementation(project(":ok-walls-common"))

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
