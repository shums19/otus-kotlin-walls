plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
}

dependencies {
    val ktorVersion: String by project
    val logbackVersion: String by project
    val kotestVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("io.ktor:ktor-serialization-jackson")
    implementation("io.ktor:ktor-server-content-negotiation")

    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    implementation(project(":ok-walls-api-v1"))
    implementation(project(":ok-walls-mappers-v1"))
    implementation(project(":ok-walls-common"))
    implementation(project(":ok-walls-biz"))
    implementation(project(":ok-walls-stubs"))
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
