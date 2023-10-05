rootProject.name = "otus-kotlin-walls"

include("ok-walls-acceptance")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}
