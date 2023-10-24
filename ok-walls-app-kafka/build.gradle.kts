plugins {
    kotlin("jvm")
}

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    val kotestVersion: String by project

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    implementation(project(":ok-walls-common"))
    implementation(project(":ok-walls-api-v1"))
    implementation(project(":ok-walls-mappers-v1"))
    implementation(project(":ok-walls-biz"))
    implementation(project(":ok-walls-repo-inmemory"))
    implementation(project(":ok-walls-repo-cassandra"))

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
