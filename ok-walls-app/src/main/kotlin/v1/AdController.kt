package ru.otus.kotlin.walls.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.kotlin.walls.biz.AdProcessor

fun Route.v1(processor: AdProcessor) {
    route("ad") {
        post("create") {
            call.createAd(processor)
        }
        post("read") {
            call.readAd(processor)
        }
        post("update") {
            call.updateAd(processor)
        }
        post("delete") {
            call.deleteAd(processor)
        }
        post("search") {
            call.searchAd(processor)
        }
    }
}
