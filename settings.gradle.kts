rootProject.name = "otus-kotlin-walls"

include("ok-walls-acceptance")
include("ok-walls-api-v1")
include("ok-walls-common")
include("ok-walls-mappers-v1")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}
