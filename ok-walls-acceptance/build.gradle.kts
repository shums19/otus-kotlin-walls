plugins {
    kotlin("jvm")
}

dependencies {
    val testContainersVersion: String by project
    val kotestVersion: String by project
    val ktorVersion: String by project
    val kotlinLoggingJvmVersion: String by project

    testImplementation(kotlin("stdlib"))

    testImplementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")

    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")

    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
    test {
        systemProperty("kotest.framework.test.severity", "NORMAL")
    }
    create<Test>("test-strict") {
        systemProperty("kotest.framework.test.severity", "MINOR")
        group = "verification"
    }
}
