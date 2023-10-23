package ru.otus.kotlin.walls.app

import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.slf4j.event.Level
import ru.otus.kotlin.walls.api.v1.apiV1Mapper
import ru.otus.kotlin.walls.app.v1.v1
import ru.otus.kotlin.walls.biz.AdProcessor

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.prepare(processor: AdProcessor = AdProcessor()) {

    install(DefaultHeaders)

    install(CallLogging) {
        level = Level.INFO
    }

    routing {
        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }

            v1(processor)
        }
    }
}
