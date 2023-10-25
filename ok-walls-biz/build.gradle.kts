plugins {
    kotlin("jvm")
}

dependencies {
    val kotestVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":ok-walls-common"))
    implementation(project(":ok-walls-stubs"))
    implementation(project(":ok-walls-lib-cor"))
    implementation(project(":ok-walls-auth"))
    implementation(project(":ok-walls-repo-inmemory"))

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation(project(":ok-walls-repo-stubs"))
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
