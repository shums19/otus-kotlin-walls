package ru.otus.kotlin.walls.app.v1

import io.ktor.server.application.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.kotlin.walls.api.v1.models.AdCreateRequest
import ru.otus.kotlin.walls.api.v1.models.AdDeleteRequest
import ru.otus.kotlin.walls.api.v1.models.AdReadRequest
import ru.otus.kotlin.walls.api.v1.models.AdSearchRequest
import ru.otus.kotlin.walls.api.v1.models.AdUpdateRequest
import ru.otus.kotlin.walls.app.base.toModel
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.mappers.v1.fromTransport
import ru.otus.kotlin.walls.mappers.v1.toTransportCreate
import ru.otus.kotlin.walls.mappers.v1.toTransportDelete
import ru.otus.kotlin.walls.mappers.v1.toTransportRead
import ru.otus.kotlin.walls.mappers.v1.toTransportSearch
import ru.otus.kotlin.walls.mappers.v1.toTransportUpdate

suspend fun ApplicationCall.createAd(processor: AdProcessor) {
    val request = receive<AdCreateRequest>()
    val context = AdContext()
    context.fromTransport(request)
    context.principal = this.request.call.principal<JWTPrincipal>().toModel()
    processor.exec(context)
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readAd(processor: AdProcessor) {
    val request = receive<AdReadRequest>()
    val context = AdContext()
    context.fromTransport(request)
    context.principal = this.request.call.principal<JWTPrincipal>().toModel()
    processor.exec(context)
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAd(processor: AdProcessor) {
    val request = receive<AdUpdateRequest>()
    val context = AdContext()
    context.fromTransport(request)
    context.principal = this.request.call.principal<JWTPrincipal>().toModel()
    processor.exec(context)
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteAd(processor: AdProcessor) {
    val request = receive<AdDeleteRequest>()
    val context = AdContext()
    context.fromTransport(request)
    context.principal = this.request.call.principal<JWTPrincipal>().toModel()
    processor.exec(context)
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchAd(processor: AdProcessor) {
    val request = receive<AdSearchRequest>()

    val context = AdContext()
    context.fromTransport(request)
    context.principal = this.request.call.principal<JWTPrincipal>().toModel()
    processor.exec(context)
    respond(context.toTransportSearch())
}
